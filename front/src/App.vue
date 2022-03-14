<template>
  <div>
    <MainHeader class="main-header"/>
    <router-view class="router-view"/>
    <MainFooter/>
    <div class="chat-container">
      <ChatView :chat="getSelectedChat" v-if="getSelectedChat != null"/>
      <div class="buttons">
        <ChatButton class="chat-button" v-for="chat in getChats" :key="chat.postId" :chat="chat"/>
      </div>
    </div>
  </div>
</template>

<script>
import MainHeader from './views/MainHeader.vue'
import MainFooter from '@/views/MainFooter'
import ChatButton from '@/components/ChatButton'
import ChatView from '@/views/ChatView'
import { emitter } from '@/socketjs/SocketClient'

export default {
  components: {
    ChatView,
    ChatButton,
    MainFooter,
    MainHeader
  },
  computed: {
    getChats() {
      return this.$store.state.chats
    },
    getSelectedChat() {
      return this.$store.state.selectedChat
    }
  },
  mounted() {
    emitter.on('onReceiveWSToken', (token) => {
      console.log('[App] received WSToken', token)
      this.ws.setToken(token)
      this.ws.connect()
    })
  }
}
</script>

<style lang="scss">

@import url(https://fonts.googleapis.com/css?family=Open+Sans);

#app {
  font-family: 'Open Sans', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

* {
  margin: 0;
  padding: 0;
}

.router-view {
  min-height: 800px;
}

.main-header {
  z-index: 1;
  position: sticky;
  top:0;
}

.chat-container {
  position: fixed;
  display: flex;
  right: 0;
  bottom: 0;
  margin-right: 20px;
  margin-bottom: 10px;

  .buttons {
    display: flex;
    flex-direction: column;
    margin-left: 10px;

    .chat-button {
      margin-top: auto;
    }
  }
}

</style>
