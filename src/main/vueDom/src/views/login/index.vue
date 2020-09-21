<template>
  <div class="login-container">
    <div class="out-login-web">
      <div class="fix">
        <template v-if="domType === 0">
          <h2 class="title">登录</h2>
          <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">
            <el-form-item prop="username">
              <el-input
                ref="username"
                v-model.trim="loginForm.username"
                placeholder="手机号、邮箱"
                name="username"
                type="text"
                tabindex="1"
                auto-complete="on"
              />
            </el-form-item>
            <el-form-item prop="password">
              <el-input
                :key="passwordType"
                ref="password"
                v-model.trim="loginForm.password"
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
          </el-form>
          <div style="text-align: center">
            <el-button :loading="loading" type="primary" @click.native.prevent="loginAjax">登录</el-button>
          </div>
        </template>

        <template v-if="domType === 1">
          <h2 class="title">注册</h2>
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
            <el-form-item v-if="domType === 1 || domType === 2 || domType === 3" prop="code">
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
          <div style="text-align: center">
            <el-button :loading="loading" type="primary" @click.native.prevent="registerAjax('register', 'registeredForm')">注册</el-button>
          </div>
        </template>

        <template v-if="domType === 2">
          <h2 class="title">忘记密码</h2>
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
          <div style="text-align: center">
            <el-button :loading="loading" type="primary" @click.native.prevent="registerAjax('forget', 'registeredForm')">提交</el-button>
          </div>
        </template>

        <template v-if="domType === 3">
          <h2 class="title">验证码登录</h2>
          <el-form ref="captcha" :model="captchaForm" :rules="captchaRules" class="login-form" auto-complete="on" label-position="left">
            <el-form-item prop="username">
              <el-input
                ref="username"
                v-model.trim="captchaForm.username"
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
                v-model.trim="captchaForm.code"
                placeholder="请输入验证码"
                name="code"
                type="number"
                auto-complete="on"
                style="width: 63%"
              />
              <el-button type="primary" style="width: 140px;margin-left: 10px" @click.native.prevent="sendCodeAjax('captchaForm')">获取验证码<span v-show="!show" class="count">({{count}}s)</span></el-button>
            </el-form-item>
          </el-form>
          <div style="text-align: center">
            <el-button :loading="loading" type="primary" @click.native.prevent="captchaLoginAjax">登录</el-button>
          </div>
        </template>

        <div class="switch-dot">
          <el-link v-if="domType !== 0" @click="domType = 0">密码登录</el-link>
          <el-link v-if="domType !== 3" @click="domType = 3">验证码登录</el-link>
          <el-link v-if="domType !== 1" @click="domType = 1">注册</el-link>
          <el-link v-if="domType !== 2" @click="domType = 2">忘记密码？</el-link>
        </div>
      </div>
      <div class="fix">
        <div></div>
      </div>
    </div>
  </div>
</template>

<script>
  import index from './index.js'
  export default index
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg:#283443;
$light_gray:#fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  /*.el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }*/

  /*.el-form-item {*/
  /*  border: 1px solid #edf2f7;*/
  /*  background: #f7fafc;*/
  /*  border-radius: 5px;*/
  /*  color: #454545;*/
  /*  input {*/
  /*    color: #000;*/
  /*    &:focus{*/
  /*      background-color: #FFF;*/
  /*      border-color: #cbd5e0;*/
  /*    }*/
  /*  }*/
  /*}*/
}
</style>

<style lang="scss" scoped>
@import "style.scss";
</style>
