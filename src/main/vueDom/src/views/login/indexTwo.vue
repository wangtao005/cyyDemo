<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">

      <div class="title-container">
        <h3 class="title">{{styleLoginName(loginType)}}</h3>
      </div>

      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
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

      <el-form-item v-if="loginType === 1 || loginType === 2 || loginType === 3" prop="code">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          ref="username"
          v-model.trim="loginForm.code"
          placeholder="请输入验证码"
          name="code"
          type="number"
          auto-complete="on"
          style="width: 58%"
        />
        <el-button type="primary" style="width: 150px" @click.native.prevent="sendCodeAjax">获取验证码<span v-show="!show" class="count">({{count}}s)</span></el-button>
      </el-form-item>
      <el-form-item prop="password" v-if="loginType !== 3">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
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
        <!-- @keyup.enter.native="handleLogin" -->
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>

      <el-form-item v-if="loginType === 1 || loginType === 2" prop="repeatPassword">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :key="passwordType"
          ref="password"
          v-model.trim="loginForm.repeatPassword"
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

      <el-button v-show="loginType === 0" :loading="loading" type="primary" style="width:100%;margin-bottom:30px;margin-left: 0;" @click.native.prevent="handleLogin('login')">登录</el-button>
      <el-button v-show="loginType === 1" :loading="loading" type="primary" style="width:100%;margin-bottom:30px;margin-left: 0;" @click.native.prevent="handleLogin('register')">注册</el-button>
      <el-button v-show="loginType === 2" :loading="loading" type="primary" style="width:100%;margin-bottom:30px;margin-left: 0;" @click.native.prevent="handleLogin('forget')">修改密码</el-button>
      <el-button v-show="loginType === 3" :loading="loading" type="primary" style="width:100%;margin-bottom:30px;margin-left: 0;" @click.native.prevent="handleLogin('codeLogin')">登录</el-button>

      <div class="tips">
        <span style="margin-right:20px;cursor: pointer" @click.prevent="swatchBut('register')">注册</span>
        <span style="cursor: pointer;margin-right:20px;" @click.prevent="swatchBut('forget')"> 忘记密码!</span>
        <span style="cursor: pointer" @click.prevent="swatchBut('codeLogin')"> 验证码登录!</span>
      </div>

    </el-form>
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
  .el-input {
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
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style lang="scss" scoped>
@import "style.scss";
</style>
