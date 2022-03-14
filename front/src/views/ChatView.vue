<template>
  <div class="main">
    <div class="user-info">
      <div class="info">
        <img ref="user-image" src="@/assets/user.png"/>
        <p class="username">{{ getSelectedChat().name }}</p>
      </div>
      <img class="close-button" src="@/assets/close.png" @click="actionClose"/>
    </div>
    <div ref="talk-scroll" class="talk-scroll">
      <div class="talk-container">
        <div class="talk_wrap"
             v-for="message in getMessages"
             :style="getMyId === message.from ? chatMessageStyle.me : chatMessageStyle.counterpart"
             :key="message.id">
          <p class="chat-message">{{ message.message }}</p>
        </div>
      </div>
    </div>
    <input @keyup.enter="actionSendMessage" placeholder="메시지를 입력하세요" v-model="inputText"/>
  </div>
</template>

<script>
import userService from '@/services/userService'

export default {
  name: 'ChatView',
  data() {
    return {
      inputText: '',
      chatMessageStyle: {
        me: {
          background: 'lightgray',
          alignSelf: 'end',
          padding: '5px 10px',
          borderRadius: '10px',
          maxWidth: '70%',
          marginBottom: '5px',
          color: 'black'
        },
        counterpart: {
          background: '#8bd0ff',
          alignSelf: 'start',
          padding: '5px 10px',
          borderRadius: '10px',
          maxWidth: '70%',
          marginBottom: '5px',
          color: 'black'
        }
      }
    }
  },
  computed: {
    getMessages() {
      return this.$store.state.selectedChatRoom.messages
    },
    getMyId() {
      return this.$store.state.user.id
    }
  },
  methods: {
    actionSendMessage() {
      if (this.inputText.length === 0) {
        return
      }
      const chatRoom = this.getSelectedChat()
      chatRoom.messages.push({
        from: this.$store.state.user.id,
        to: chatRoom.id,
        postId: chatRoom.postId,
        message: this.inputText
      })

      this.ws.send(chatRoom.id, chatRoom.postId, this.inputText)
      this.inputText = ''
      this.moveToBottom()
    },
    getSelectedChat() {
      return this.$store.state.selectedChatRoom
    },
    actionClose() {
      this.$store.state.selectedChatRoom = null
    },
    moveToBottom() {
      const el = this.$refs['talk-scroll']
      el.scrollTop = el.scrollHeight + 50
    }
  },
  mounted() {
    this.moveToBottom()
    const chatRoom = this.getSelectedChat()
    if (chatRoom.imageUrl != null) {
      userService.getUserImage(chatRoom.imageUrl)
        .then(res => {
          this.$refs['user-image'].src = res
        })
    }
  }
}
</script>

<style lang="scss" scoped>

.main {
  background-color: white;
  width: 300px;
  height: 400px;
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
      display: flex;
      flex-direction: column;

      .talk_wrap {
        word-wrap: break-word;
        .chat-message {
          font-size: 16px;
          text-align: end;
        }
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
