<template>
  <div class="main" @click="actionToggleChatView">
    <div class="info">
      <img class="user-image" :src="getUserImage"/>
      <p class="username">{{ chatRoom.name }}</p>
    </div>
    <img class="notification" src="@/assets/notification.png" v-if="chatRoom.hasNewMessage">
    <img class="close-button" src="@/assets/close.png" @click.stop="actionClose" />
  </div>
</template>

<script>
export default {
  name: 'ChatButton',
  props: ['chatRoom'],
  computed: {
    getUserImage() {
      if (this.chatRoom.imageUrl != null) {
        return null
      } else {
        return require('@/assets/user.png')
      }
    }
  },
  methods: {
    actionToggleChatView() {
      console.log('[ChatButton] select chat room - ' + this.chatRoom.id)
      const curr = this.$store.state.selectedChatRoom
      if (curr == null || curr.id !== this.chatRoom.id) {
        this.$store.dispatch('selectChatRoom', this.chatRoom)
      } else {
        this.$store.dispatch('selectChatRoom', null)
      }
    },
    actionClose() {
      this.$store.commit('removeChatRoom', this.chatRoom.id)
    }
  }

}
</script>

<style lang="scss" scoped>

.main {
  display: flex;
  align-items: center;
  justify-content: space-between;

  border: 1px solid lightgray;
  border-radius: 10px;
  height: 40px;
  width: 200px;
  background-color: white;
  padding: 5px 10px;

  .info {
    height: 100%;
    display: flex;
    align-items: center;

    .user-image {
      height: 70%;
      border-radius: 50%;
      margin-right: 10px;
    }

    .username {
      font-size: 14px;
      font-weight: bold;
    }
  }

  .notification {
    height: 50%;
    max-height: 50%;
  }

  .close-button {
    height: 40%;
  }

  .close-button:hover {
    cursor: pointer;
  }
}

.main:hover {
  cursor: pointer;
}

</style>
