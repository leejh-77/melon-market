<template>
  <div class="container">
    <img ref="post-image" class="picture" alt="image"/>
    <p class="name">{{ post.title }}</p>
    <p class="price">{{ post.price }} won</p>
    <p class="location">{{ post.location }}</p>
    <div class="extra">
      <p class="interests">interests {{ post.interestCount }}</p>
      <p>*</p>
      <p class="chats">chats {{ post.chatCount }}</p>
    </div>
  </div>
</template>
<script>
import postService from '@/services/postService'
import { Buffer } from 'buffer'

export default {
  name: 'PostCard',
  props: ['post'],
  mounted() {
    postService.getPostImage(this.post.imageUrl)
      .then(res => {
        const base64 = Buffer.from(res.data, 'binary').toString('base64')
        this.$refs['post-image'].src = 'data:image/jpg;base64, ' + base64
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
  }

  .name {
    font-size: 20px;
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
    }
  }
}

</style>
