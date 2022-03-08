<template>
  <div class="container">
    <img ref="post-image" class="picture" alt="image" @click="$emit('onClickImage')"/>
    <p class="name">{{ post.title }}</p>
    <p class="price">{{ new Intl.NumberFormat().format(post.price) }}원</p>
    <p class="location">{{ post.location }}</p>
    <div class="extra">
      <p class="likes">좋아요 {{ post.likeCount }}</p>
      <p> · </p>
      <p class="chats">채팅 {{ post.chatCount }}</p>
    </div>
  </div>
</template>
<script>
import postService from '@/services/postService'

export default {
  name: 'PostCard',
  props: ['post'],
  mounted() {
    postService.getPostImage(this.post.imageUrl)
      .then(res => {
        this.$refs['post-image'].src = res
      })
      .catch(e => {
        console.log(e)
      })
  }
}
</script>
<style lang="scss" scoped>

.container {
  text-align: left;

  p {
    margin-top: 2px;
  }

  .picture {
    width: 220px;
    height: 220px;
    background: white;
    border-radius: 20px;
    object-fit: cover;
  }

  .picture:hover {
    cursor: pointer;
  }

  .name {
    margin-top: 8px;
    font-size: 18px;
    color: black;
  }

  .price {
    color: black;
    font-weight: bold;
  }

  .location {
    color: black;
    font-size: 13px;
  }

  .extra {
    display: flex;

    p {
      margin-right: 5px;
      color: gray;
      font-size: 14px;
    }
  }
}

</style>
