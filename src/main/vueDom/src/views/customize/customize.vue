<template>
  <div id="customize">
    <wt-header title="个性化定制" dost="Personalized Customization"></wt-header>
    <div style="margin-top: 30px"></div>
    <div class="center-web">
      <div class="center-web-ot">
        <div class="fix">
          <p class="txt-cost">若您有更复杂的实验程序需求，但是当前平台的功能无法满足，您可以联系我们，以获得个性化定制的服务。我们可以根据您的需求（比如，线上多人同时互动的实验，线上复杂网络实验等）单独为您的实验程序做最优质的服务。</p>
          <p class="txt-cost">请在右侧填写您的信息和需求，后期技术人员会第一时间联系您。</p>
        </div>
        <div class="fix border-web"></div>
        <div class="fix">
          <el-form label-position="top" label-width="300" :model="formData" :rules="rules" ref="formData">
            <el-form-item label="登录账号">
              <el-input v-model="formData.account" placeholder="请输入登录账号(选填)"></el-input>
            </el-form-item>
            <el-form-item label="姓名" prop="userName">
              <el-input v-model="formData.userName" placeholder="请输入姓名"></el-input>
            </el-form-item>
            <el-form-item label="联系电话" prop="userPhone">
              <el-input v-model="formData.userPhone" placeholder="请输入正确的联系电话"></el-input>
            </el-form-item>
            <el-form-item label="联系邮件" prop="userEmail">
              <el-input v-model="formData.userEmail" placeholder="请输入正确的联系邮件"></el-input>
            </el-form-item>
            <el-form-item label="需求描述" prop="remark">
              <el-input type="textarea" v-model="formData.remark" placeholder="请输入需求描述" :autosize="{minRows: 6}"></el-input>
            </el-form-item>
          </el-form>
        </div>
      </div>
      <div style="text-align: center;margin-bottom: 20px">
        <el-button type="primary" style="width: 100px" @click="submitForm">提交</el-button>
      </div>
    </div>
  </div>
</template>

<script>
  import wtHeader from "@/components/wtHeader/wtHeader";
  import personaliseSave from "@/api/personaliseSave";
  export default {
    name: "customize",
    components: {
      wtHeader
    },
    data() {
      return {
        formData: {
          account: '',
          userName: '',
          userPhone: '',
          userEmail: '',
          detail: ''
        },
        rules: {
          userName: [
            { required: true, message: '姓名不能为空'}
          ],
          userPhone: [
            { required: true, message: '联系电话不能为空'}
          ],
          remark: [
            { required: true, message: '需求描述不能为空'}
          ]
        }
      }
    },
    methods: {
      copyTxt(str, len) {
        let txt = ''
        for (let i = 0; i < len; i++) {
          txt += str
        }
        return txt
      },
      submitForm() {
        this.$refs.formData.validate((valid) => {
          if (valid) {
            personaliseSave({
              ...this.formData
            }).then(res => {
              if (res.code === 200) {
                this.$confirm('提交成功', '提示', {
                  confirmButtonText: '确定',
                  showCancelButton: false,
                  type: 'warning'
                }).then(() => {

                })
              }
            })
          } else {

            return false;
          }
        });
      }
    }
  }
</script>

<style scoped lang="scss">
@import "./style.scss";
</style>