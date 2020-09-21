<template>
  <div class="dashboard-container">
    <wt-header title="我的账户" dost="myAccount"></wt-header>
    <div class="dashboard-text">
      <h2 class="title-tis">注册信息</h2>
      <el-form :model="nodeForm" label-width="200" label-position="left">
        <el-form-item label="用户名:">
          {{nodeForm.userName}}
        </el-form-item>
        <el-form-item label="邮件地址:" v-if="false">
          {{nodeForm.Email}}
        </el-form-item>
        <el-form-item label="账户类型:">
          {{!nodeForm.type ? '免费用户' : '付费用户'}}
        </el-form-item>
        <el-form-item label="密码:">
          <el-link @click="dialogVisible2 = true">修改密码</el-link>
        </el-form-item>
        <el-form-item label="充值情况:">
          {{`${nodeForm.startTime}至${nodeForm.endTime}`}}<el-button type="warning" size="mini" style="margin-left: 10px;position: relative;top: -1px;" @click="obtainVip()">充值</el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-dialog
      title="升级"
      class="dashboard-dialog"
      :visible.sync="dialogVisible"
      width="30%">
      <div class="contr-cor">
        <div class="box-web" v-for="(item, index) in vipInfoList" :class="{'active': active === index}" @click="active = index">
          <h2 class="box-web-title">{{item.levelName}}</h2>
          <p class="box-web-ext">{{item.duration}}天{{item.amount}}元</p>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="obtainVipEt">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="扫码支付"
      class="dashboard-dialog"
      :visible.sync="dialogVisible3"
      width="400px">
      <div class="contr-cor" style="position: relative">
        <qrcode-vue style="margin: 0 auto" :value="codeUrl" :size="200" level="H"></qrcode-vue>
        <div style="position: absolute;top: 0;left: 0;right: 0;bottom: 0;width: 100%;height: 100%;display: flex;align-items: center;justify-content: center">
          <div v-if="playType === 0" style="width: 80px;height: 80px;display: flex;align-items: center;justify-content: center;background-color: #00aa88;border-radius: 50%">
            <i class="el-icon-check" style="color: #FFF;font-size: 60px"></i>
          </div>
          <div v-else-if="playType === 1" style="width: 80px;height: 80px;display: flex;align-items: center;justify-content: center;background-color: red;border-radius: 50%">
            <i class="el-icon-close" style="color: #FFF;font-size: 60px"></i>
          </div>
        </div>
      </div>
      <p v-if="active !== ''" style="text-align: center;font-size: 18px;color: #000;margin: 4px 0;">金额:{{vipInfoList[active].amount}}</p>
      <p v-if="active !== ''" style="text-align: center;font-size: 18px;color: #000;margin: 4px 0;">{{vipInfoList[active].duration}}天</p>
      <span slot="footer" class="dialog-footer" style="display: none">
        <el-button @click="dialogVisible3 = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible3 = false">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="修改密码"
      class="dashboard-dialog"
      :visible.sync="dialogVisible2"
      width="450px">
      <div class="dashboard-dialog-form">
        <el-form ref="registered" :model="registeredForm" :rules="registeredRules" class="login-form" auto-complete="on" label-position="left">
          <el-form-item prop="username">
            <el-input
              ref="username"
              v-model.trim="registeredForm.username"
              placeholder="手机号、邮箱"
              name="username"
              type="text"
              tabindex="1"
              auto-complete="on"
            />
          </el-form-item>
          <el-form-item prop="code">
            <el-input
              ref="username"
              v-model.trim="registeredForm.code"
              placeholder="请输入验证码"
              name="code"
              type="number"
              auto-complete="on"
              style="width: 63%"
            />
            <el-button type="primary" style="width: 140px;margin-left: 10px" @click.native.prevent="sendCodeAjax('registeredForm')">获取验证码<span v-show="!show" class="count">({{count}}s)</span></el-button>
          </el-form-item>
          <el-form-item prop="password">
            <el-input
              :key="passwordType"
              ref="password"
              v-model.trim="registeredForm.password"
              :type="passwordType"
              placeholder="登录密码"
              name="password"
              tabindex="2"
              auto-complete="on"
            />
            <span class="show-pwd" @click="showPwd">
              <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
            </span>
          </el-form-item>
          <el-form-item prop="repeatPassword">
            <el-input
              :key="passwordType"
              ref="password"
              v-model.trim="registeredForm.repeatPassword"
              :type="passwordType"
              placeholder="重复登录密码"
              name="password"
              tabindex="2"
              auto-complete="on"
            />
            <span class="show-pwd" @click="showPwd">
              <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
            </span>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible2 = false">取 消</el-button>
        <el-button type="primary" @click.native.prevent="registerAjax('forget', 'registeredForm')">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import wtHeader from "@/components/wtHeader/wtHeader";
import {validUsername} from "@/utils/validate";
import sendCode from "@/api/sendCode";
import vipInfo from "@/api/vipinfo";
import orderWeiXinNative from "@/api/orderWeiXinNative";
import orderFindOrder from "@/api/orderFindOrder";
import getInfo from "@/api/getInfo";
import { getToken } from '@/utils/auth' // get token from cookie
import QrcodeVue from 'qrcode.vue'
const TIME_COUNT = 60; //更改倒计时时间
export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name'
    ])
  },
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
      nodeForm: {
        userName: '',
        Email: '22',
        type: '免费试用版',
        startTime: '',
        endTime: ''
      },
      dialogVisible: false,
      dialogVisible2 : false,
      dialogVisible3: false,
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
      passwordType: 'password',
      show: true,
      count: '', // 初始化次数
      vipInfoList: [],
      active: '',
      playType: null,
      codeUrl: "",
      orderId: "",
      timeName: null
    }
  },
  created() {
    this.getVipInfoList()
    this._getInfo()
  },
  methods: {
    obtainVip() {
      if (!getToken()) { // 未登陆
        this.$confirm('请登录', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$router.push({
            path: `/login?redirect=${this.$route.path}`
          })
        }).catch(() => {

        });
      } else {
        this.dialogVisible = true
      }
    },
    obtainVipEt() {

      if (this.active !== '') {
        orderWeiXinNative({
          amount: this.vipInfoList[this.active].amount,
          goodsDescription: this.vipInfoList[this.active].duration,
          goodsId: this.vipInfoList[this.active].id
        }).then((res) => {
          this.codeUrl = res.codeUrl
          this.orderId = res.orderId
          let out = setTimeout(() => {
            clearTimeout(out)
            this.dialogVisible3 = true
            this.pollingPay()
          }, 20)
        }).catch(error => {
          console.log(error)
          this.$confirm(error, '错误', {
            confirmButtonText: '确定',
            type: "error"
          })
        })
      } else {
        this.dialogVisible = false
      }
    },
    pollingPay() {
      clearTimeout(this.timeName)
      this.timeName = setTimeout(() => {
        orderFindOrder({
          id: this.orderId
        }).then(res => {
          console.log(res)
          if (res.state === 'PAY_FINAL_SUCCESS') {
            this.playType = 0
            setTimeout(() => {
              window.location.reload()
            }, 2000)
          } else if (res.state === 'PAY_FINAL_FAIL') {
            this.playType = 1
          } else {
            if (this.dialogVisible3) {
              this.pollingPay()
            }
          }
        })
      }, 2000)
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
    getVipInfoList() {
      vipInfo().then(res => {
        console.log(res)
        if (res.code === 200) {
          this.vipInfoList = res.data
        }
      })
    },
    _getInfo() {
      getInfo().then(res => {
        console.log(res)
        if (res.code === 200) {
          this.nodeForm = res.data
        }
      })
    }
  },
  components: {
    wtHeader,
    QrcodeVue
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 10px;
  }
  &-text {
    width: 900px;
    margin: 40px auto 0 auto;
    box-shadow: 0px -1px 7px 2px #8c8c8c8c;
    border-radius: 3px;
    overflow: hidden;
    padding: 10px;
    .title-tis{
      font-size: 22px;
      line-height: 1;
      margin: 10px 0;
    }
    .el-form{
      &-item{
        margin-bottom: 0;
        .el-link{
          line-height: 1;
          vertical-align: baseline;
        }
      }
    }
  }
  &-dialog{
    ::v-deep.contr-cor{
      display: flex;
      .box-web{
        flex: 1;
        height: 100px;
        background-color: #DDD;
        border: 1px solid #DDD;
        margin: 10px;
        padding: 10px;
        box-sizing: border-box;
        border-radius: 3px;
        cursor: pointer;
        &-title{
          margin: 0;
          line-height: 1;
        }
        &-ext{
          font-size: 16px;
          margin: 10px 0 0 0;
        }
        &.active{
          border-color: #d6d6d6;
          background-color: #eaf8ff;
        }
      }
    }
    &-form{
      .show-pwd {
        position: absolute;
        right: 10px;
        top: 0px;
        font-size: 16px;
        cursor: pointer;
        user-select: none;
      }
    }
  }
}
</style>
