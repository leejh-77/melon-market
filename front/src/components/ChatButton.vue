<template>
  <div class="main" @click="actionToggleChatView">
    <img :src="getUserImage"/>
    <p class="username">{{ this.chatRoom.name }}</p>
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
      const curr = this.$store.state.selectedChat
      if (curr == null || curr.id !== this.chatRoom.id) {
        this.$store.commit('selectChatRoom', this.chatRoom)
      } else {
        this.$store.commit('selectChatRoom', null)
      }
    }
  }

}
</script>

<style lang="scss" scoped>

.main {
  display: flex;
  align-items: center;
  border: 1px solid lightgray;
  border-radius: 10px;
  height: 40px;
  width: 200px;
  background-color: white;
  padding: 5px 10px;

  img {
    height: 70%;
    border-radius: 50%;
    margin-right: 10px;
  }

  .username {
    font-size: 14px;
    font-weight: bold;
  }
}

.main:hover {
  cursor: pointer;
}

</style>
