<template>
  <div></div>
</template>

<script>
import postService from '@/services/postService'

export default {
  name: 'PostDetailView',
  data() {
    return {
      post: null
    }
  },
  methods: {},
  mounted() {
    const postId = this.$route.params.postId
    postService.getPost(postId)
      .then(res => {
        console.log(res.data)
        this.post = res.data
      })
  }
}
</script>

<style scoped>

</style>
