<template>
  <div>
    <UserInfoModal ref="user-info-modal"/>
    <MainHeader class="main-header" @showEditInfoModal="actionShowEditInfoModal"/>
    <router-view class="router-view"/>
    <MainFooter/>
    <div class="chat-container">
      <ChatView :chat="getSelectedChatRoom" v-if="getSelectedChatRoom != null"/>
      <div class="buttons">
        <ChatButton class="chat-button" v-for="chatRoom in getChatRooms" :key="chatRoom.id" :chatRoom="chatRoom"/>
      </div>
    </div>
  </div>
</template>

<script>
import MainHeader from './views/MainHeader.vue'
import MainFooter from '@/views/MainFooter'
import ChatButton from '@/components/ChatButton'
import ChatView from '@/views/ChatView'
import UserInfoModal from '@/components/UserInfoModal'

export default {
  components: {
    UserInfoModal,
    ChatView,
    ChatButton,
    MainFooter,
    MainHeader
  },
  computed: {
    getChatRooms() {
      return this.$store.state.chatRooms
    },
    getSelectedChatRoom() {
      return this.$store.state.selectedChatRoom
    }
  },
  methods: {
    actionShowEditInfoModal() {
      this.$refs['user-info-modal'].show()
    }
  },
  mounted() {
    this.emitter.on('onReceiveWSToken', (token) => {
      console.log('[App] received WSToken', token)
      this.ws.onMessageReceived = (data) => {
        this.$store.dispatch('pushMessage', data)
      }
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
  top: 0;
}

.chat-container {
  position: fixed;
  display: flex;
  right: 0;
  bottom: 0;
  margin-right: 50px;
  margin-bottom: 30px;

  .buttons {
    display: flex;
    flex-direction: column;
    margin-left: 10px;
    margin-top: auto;
  }
}

</style>
