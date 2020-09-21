import noFeedback from '@/views/noFeedback/index.vue'
export default {
  name: "examine",
  components: {
    noFeedback
  },
  data() {
    return {
      infoId: '',
    }
  },
  created() {
    this.infoId = this.$route.params.id
  }
}
