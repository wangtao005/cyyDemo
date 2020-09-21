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
      } else if (value !== this.loginForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    }
    return {
      loginForm: {
        username: '',
        password: '',
        code: '',
        id: ''
      },
      loginRules: {
        username: [{ required: true, trigger: ['blur', 'change'], validator: validateUsername }],
        code: [{ required: true, trigger: ['blur', 'change'], message: '请输入验证码' }],
        password: [{ required: true, trigger: ['blur', 'change'], validator: validatePassword }],
        repeatPassword: [{ required: true, trigger: ['blur', 'change'], validator: validatePassword2 }]
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined,
      loginType: 0,
      loginTs: {
        0: '登录',
        1: '注册',
        2: '找回密码',
        3: '登录'
      },
      show: true,
      count: '', // 初始化次数
      timer: null,
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
    sendCodeAjax() {
      if (!validUsername(this.loginForm.username)) {
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
          account: this.loginForm.username,
          type: 1
        }).then(res => {
          if (res.code === 200) {
            this.loginForm.id = res.id
          }
        })
      }
    },
    swatchBut(type) {
      switch (type) {
        case 'register':
          this.loginType = 1
          // this.loginRules.repeatPassword[0].required = true
          // this.loginRules.code[0].required = true
          break;
        case 'forget':
          this.loginType = 2
          break;
        case 'codeLogin':
          this.loginType = 3
          break;
      }
    },
    handleLogin(type) {
      switch (type) {
        case 'login':
          this.loginAjax()
          break;
        case 'register':
          this.registerAjax('register')
          break;
        case 'forget':
          this.registerAjax('forget')
          break;
        case 'codeLogin':
          this.registerAjax('slogin')
          break;
      }
    },
    registerAjax(type) {
      // this.loginRules.repeatPassword[0].required = true
      // this.loginRules.code[0].required = true
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.$store.dispatch(`user/${type}`, this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || '/' })
            this.loading = false
          }).catch(() => {
            this.loading = false
          })
        } else {
          console.log('error submit!!')
          this.loading = false
          return false
        }
      })
    },
    loginAjax() {
      this.$refs.loginForm.validate(valid => {
        console.log(valid)
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
    }
  }
}
