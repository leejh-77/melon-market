<template>
  <div class="main">
    <div class="container">
      <ImagePager :images="images"/>
      <div class="user-info">
        <img :src="getUserImage"/>
        <p class="name">{{ user.username }}</p>
        <p class="region">{{ post.region }}</p>
      </div>
      <div class="line"/>
      <div class="content">
        <img class="chat" src="@/assets/chat.png" @click="actionChat" v-if="authenticated">
        <img class="like" :src="getLikeImageSrc" @click="actionToggleLike" v-if="authenticated">
        <p class="title">{{ post.title }}</p>
        <p class="date">{{ getDateString }}</p>
        <p class="price">{{ new Intl.NumberFormat().format(post.price) }}원</p>
        <p class="body">{{ post.body }}</p>
        <p class="meta-info">{{ getMetaInfoString }}</p>
        <div class="edit-container" v-if="isMyPost">
          <button class="edit-button" @click="actionGoToEditPost">수정</button>
          <button class="delete-button" @click="actionDeletePost">삭제</button>
        </div>
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
      const today = new Date().getTime()
      const day = new Date(this.post.createdTime).getTime()

      const diff = Math.ceil((today - day) / (1000 * 60 * 60 * 24))
      if (diff <= 1) {
        return '오늘'
      } else if (diff <= 2) {
        return '어제'
      } else {
        return diff + ' 일 전'
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
    },
    isMyPost() {
      if (!this.authenticated) {
        return
      }
      return this.$store.state.user.id === this.user.id
    }
  },
  data() {
    return {
      post: {
        id: 0,
        title: '',
        body: '',
        price: 0,
        createdTime: '',
        likeCount: 0,
        chatCount: 0,
        viewCount: 0,
        likedByMe: false,
        imageUrls: []
      },
      user: {
        id: 0,
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
    actionToggleLike() {
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
    },
    actionDeletePost() {
      if (confirm('정말 삭제하시겠습니까?')) {
        postService.deletePost(this.post.id)
          .then(res => {
            this.$router.push('/')
          })
          .catch(e => {

          })
      }
    },
    actionGoToEditPost() {
      this.$router.push('/post-edit/' + this.post.id)
    },
    actionChat() {
      this.$store.commit('pushChatRoom', {
        id: this.user.id,
        name: this.user.username,
        imageUrl: this.user.imageUrl,
        postId: this.post.id,
        messages: []
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

      .region {
        font-size: 14px;
        text-align: right;
        margin-left: auto;
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

      .chat {
        width: 30px;
        position: absolute;
        right: 0;
        margin-right: 45px;
      }

      .chat:hover {
        cursor: pointer;
      }

      .title {
        margin-top: 20px;
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

      .edit-container {
        display: flex;
        justify-content: right;
        margin-bottom: 60px;

        button {
          height: 40px;
          width: 120px;
          margin-left: 20px;
          font-size: 14px;
          font-weight: bold;
        }

        button:hover {
          cursor: pointer;
        }

        .edit-button {
          border: 1px solid cornflowerblue;
          border-radius: 10px;
          color: cornflowerblue;
          background-color: white;
        }

        .delete-button {
          border: none;
          border-radius: 10px;
          color: white;
          background-color: indianred;
        }
      }
    }
  }
}

</style>
