<template>
  <div class="main">
    <div class="user-info">
      <div class="info">
        <img :src="getUserImage"/>
        <p class="username">{{ getSelectedChat().name }}</p>
      </div>
      <img class="close-button" src="@/assets/close.png" @click="actionClose"/>
    </div>
    <div ref="talk-scroll" class="talk-scroll">
      <div class="talk-container">
        <p v-for="message in getMessages" :key="message.id">{{ message.message }}</p>
      </div>
    </div>
    <input @keyup.enter="actionSendMessage" placeholder="메시지를 입력하세요" v-model="inputText"/>
  </div>
</template>

<script>
export default {
  name: 'ChatView',
  data() {
    return {
      inputText: ''
    }
  },
  computed: {
    getUserImage() {
      if (this.getSelectedChat().imageUrl != null) {
        return null
      } else {
        return require('@/assets/user.png')
      }
    },
    getMessages() {
      return this.$store.state.selectedChatRoom.messages
    }
  },
  methods: {
    actionSendMessage() {
      if (this.inputText.length === 0) {
        return
      }
      this.getSelectedChat().messages.push({
        id: this.$store.state.user.id,
        message: this.inputText
      })

      const chatRoom = this.getSelectedChat()
      this.ws.send(chatRoom.id, chatRoom.postId, this.inputText)
      this.inputText = ''
      const el = this.$refs['talk-scroll']
      el.scrollTop = el.scrollHeight + 10
    },
    getSelectedChat() {
      return this.$store.state.selectedChatRoom
    },
    actionClose() {
      this.$store.state.selectedChatRoom = null
    }
  }
}
</script>

<style lang="scss" scoped>

.main {
  background-color: white;
  width: 300px;
  height: 300px;
  border-radius: 10px;
  border: 1px solid lightgray;
  display: flex;
  flex-direction: column;

  .user-info {
    display: flex;
    align-items: center;
    justify-content: space-between;
    height: 40px;
    padding: 5px 10px;
    border: 1px solid lightgray;

    .info {
      display: flex;
      align-items: center;
      height: 100%;

      img {
        height: 70%;
        margin-right: 10px;
      }

      p {
        font-size: 14px;
        font-weight: bold;
      }
    }

    .close-button {
      margin-right: 10px;
      height: 40%;
    }

    .close-button:hover {
      cursor: pointer;
    }
  }

  .talk-scroll {
    overflow: scroll;
    margin-top: auto;

    .talk-container {
      text-align: left;
      padding: 10px;

      .talk {
        font-size: 16px;
      }
    }
  }

  input {
    font-size: 16px;
    padding: 10px;
    border: 1px solid lightgray;
  }
}

</style>
