<template>
  <div class="main">
    <div class="container">
      <ImagePager :images="images"/>
      <div class="user-info">
        <img :src="getUserImage"/>
        <p class="name">{{ user.username }}</p>
      </div>
      <div class="line"/>
      <div class="content">
        <img class="like" :src="getLikeImageSrc" @click="changeLike" v-if="authenticated">
        <p class="title">{{ post.title }}</p>
        <p class="date">{{ getDateString }}</p>
        <p class="price">{{ new Intl.NumberFormat().format(post.price) }}원</p>
        <p class="body">{{ post.body }}</p>
        <p class="meta-info">{{ getMetaInfoString }}</p>
        <div class="line"/>
      </div>
    </div>
  </div>
</template>

<script>
import postService from '@/services/postService'
import ImagePager from '@/components/ImagePager'

export default {
  name: 'PostDetailView',
  components: { ImagePager },
  computed: {
    getDateString() {
      const today = new Date().getDay()
      const day = new Date(this.post.createdTime).getDay()

      const diff = today - day
      switch (diff) {
        case 0: return '오늘'
        case 1: return '어제'
        default: return diff + ' 일 전'
      }
    },
    getMetaInfoString() {
      return '좋아요 ' + this.post.likeCount +
        ' · 채팅 ' + this.post.chatCount +
        ' · 조회수 ' + this.post.viewCount
    },
    getLikeImageSrc() {
      return this.post.likedByMe ? require('@/assets/like.png') : require('@/assets/not-like.png')
    },
    getUserImage() {
      if (this.user.imageUrl != null) {
        return null
      } else {
        return require('@/assets/user.png')
      }
    },
    authenticated() {
      return this.$store.state.authenticated
    }
  },
  data() {
    return {
      post: {
        title: '',
        body: '',
        price: 0,
        createdTime: '',
        likeCount: 0,
        chatCount: 0,
        viewCount: 0,
        likedByMe: false
      },
      user: {
        username: '',
        imageUrl: ''
      },
      images: []
    }
  },
  methods: {
    loadImages() {
      this.post.imageUrls.forEach(url => {
        postService.getPostImage(url)
          .then(res => {
            this.images.push(res)
          })
      })
    },
    changeLike() {
      postService.changeLike(this.post.id, !this.post.likedByMe)
        .then(res => {
          if (this.post.likedByMe) {
            this.post.likedByMe = false
            this.post.likeCount--
          } else {
            this.post.likedByMe = true
            this.post.likeCount++
          }
        })
    }
  },
  mounted() {
    const postId = this.$route.params.postId
    postService.getPost(postId)
      .then(res => {
        console.log('[GetPostDetail]', res.data)
        this.post = res.data.post
        this.user = res.data.user
        this.loadImages()
      })
      .catch(e => {
        console.log('[GetPostDetail][Error', e)
      })
  }
}
</script>

<style lang="scss" scoped>
.main {
  display: flex;
  justify-content: center;

  .container {
    margin-top: 20px;
    width: 40%;

    .user-info {
      display: flex;
      align-items: center;
      height: 60px;
      margin-top: 20px;
      margin-bottom: 10px;

      img {
        height: 70%;
        border-radius: 50%;
      }
      .name {
        font-weight: bold;
        margin-left: 10px;
      }
    }
    .line {
      height: 0.7px;
      background-color: lightgray;
    }

    .content {
      position: relative;
      .like {
        width: 30px;
        position: absolute;
        right: 0;
      }
      .like:hover {
        cursor: pointer;
      }
      .title {
        margin-top: 30px;
        margin-bottom: 6px;

        text-align: left;
        font-size: 20px;
        font-weight: bold;
      }
      .date {
        font-size: 13px;
        text-align: left;
        margin-bottom: 8px;
        opacity: 50%;
      }
      .price {
        font-size: 18px;
        text-align: left;
        font-weight: bold;
        margin-bottom: 30px;
      }
      .body {
        text-align: left;
        font-size: 16px;
        padding-bottom: 40px;
      }
      .meta-info {
        text-align: left;
        font-size: 13px;
        opacity: 60%;
        margin-bottom: 30px;
      }
    }
  }
}

</style>
