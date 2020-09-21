import { validUsername } from '@/utils/validate'
import sendCode from "@/api/sendCode";
const TIME_COUNT = 60; //更改倒计时时间
export default {
  name: 'Login',
  data() {
    const validateUsername = (rule, value, callback) => {
      if (!validUsername(value)) {
        callback(new Error('请输入手机号或者邮箱'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length === 0) {
        callback(new Error('请输入密码'))
      } else {
        callback()
      }
    }
    const validatePassword2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.registeredForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    }
    return {
      loginForm: {
        username: '',
        password: '',
      },
      loginRules: {
        username: [{ required: true, trigger: ['blur', 'change'], validator: validateUsername }],
        password: [{ required: true, trigger: ['blur', 'change'], validator: validatePassword }],
      },
      registeredForm: {
        username: '',
        password: '',
        repeatPassword: '',
        code: '',
        id: ''
      },
      registeredRules: {
        username: [{ required: true, trigger: ['blur', 'change'], validator: validateUsername }],
        code: [{ required: true, trigger: ['blur', 'change'], message: '请输入验证码' }],
        password: [{ required: true, trigger: ['blur', 'change'], validator: validatePassword }],
        repeatPassword: [{ required: true, trigger: ['blur', 'change'], validator: validatePassword2 }]
      },
      captchaForm: {
        username: '',
        code: '',
        id: ''
      },
      captchaRules: {
        username: [{ required: true, trigger: ['blur', 'change'], validator: validateUsername }],
        code: [{ required: true, trigger: ['blur', 'change'], message: '请输入验证码' }],
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined,
      loginType: 0,
      show: true,
      count: '', // 初始化次数
      timer: null,
      domType: 0, // 0登录 1注册 2找回密码 3 验证码登录
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  methods: {
    styleLoginName(type) {
      return this.loginTs[type]
    },
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    sendCodeAjax(type) {
      if (!validUsername(this[type].username)) {
        return
      }
      if (!this.timer) {
        this.count = TIME_COUNT;
        this.show = false;
        this.timer = setInterval(() => {
          if (this.count > 0 && this.count <= TIME_COUNT) {
            this.count--;
          } else {
            this.show = true;
            clearInterval(this.timer); // 清除定时器
            this.timer = null;
          }
        }, 1000)
        sendCode({
          account: this[type].username,
          type: 1
        }).then(res => {
          if (res.code === 200) {
            this[type].id = res.id
          }
        })
      }
    },
    registerAjax(type, dataType) {
      // this.loginRules.repeatPassword[0].required = true
      // this.loginRules.code[0].required = true
      this.$refs.registered.validate(valid => {
        if (valid) {
          this.$store.dispatch(`user/${type}`, this[dataType]).then(() => {
            this.$router.push({ path: this.redirect || '/' })
            this.loading = false
          }).catch(() => {
            this.loading = false
          })
        } else {
          this.loading = false
          return false
        }
      })
    },
    loginAjax() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
            this.loading = true
            this.$store.dispatch('user/login', this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || '/' })
            this.loading = false
          }).catch(() => {
            this.loading = false
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    captchaLoginAjax() {
      this.$refs.captcha.validate(valid => {
        if (valid) {
          this.$store.dispatch('user/slogin', this.captchaForm).then(() => {
            this.$router.push({ path: this.redirect || '/' })
            this.loading = false
          }).catch(() => {
            this.loading = false
          })
        } else {
          this.loading = false
          return false
        }
      })
    }
  }
}
