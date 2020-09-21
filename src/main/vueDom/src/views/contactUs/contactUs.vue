<template>
  <div id="contact-us">
    <wt-header title="联系我们" dost="contactUs"></wt-header>
    <div style="margin-top: 30px"></div>
    <article class="center-web">
      <h2 class="title-h2">关于我们</h2>
      <div class="company-profile">
        <p>简单云实验（easy_exp）是一个创建在线心理学，社会学，经济学实验的简易平台。即使没有任何编程背景，也可以轻松地创建复杂的，灵活的线上实验。</p>
        <p>您既可以用简单云实验编写实验，进行现场实验（招募实验受试者来到实验室完成实验），也可以进行“云”实验（实验受试者足不出户，可通过点击您创建的线上实验链接完成实验）。</p>
        <p>您可以用简单云实验平台，创建各种类型的问卷（可精确记录每道题目的答题时间，让您的数据更丰富更精确），创建各种类型的实验程序（感知觉实验，语言实验，记忆实验，社会认知实验，大型真人互动实验，复杂网络实验等等），甚至也可以通过我们的平台制作小游戏，与您的伙伴进行互动。</p>
      </div>
      <h2>联系邮箱</h2>
      <p>asy_exp@163.com</p>
      <h2>联系地址</h2>
      <p>联系地址联系地址联系地址联系地址联系地址联系地址联系地址联系地址联系地址</p>
    </article>
  </div>
</template>

<script>
  import wtHeader from "@/components/wtHeader/wtHeader";
///personalise/save
  export default {
    name: "contactUs",
    components: {
      wtHeader
    },
    data() {
      return {}
    },
    methods: {
      copyTxt(str, len) {
        let txt = ''
        for (let i = 0; i < len; i++) {
          txt += str
        }
        return txt
      },
    }
  }
</script>

<style scoped lang="scss">
@import "./style.scss";
</style>