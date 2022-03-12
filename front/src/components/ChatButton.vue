<template>
  <div class="main" @click="actionToggleChatView">
    <p class="title">{{ this.chat.title }}</p>
  </div>
</template>

<script>
export default {
  name: 'ChatButton',
  props: ['chat'],
  methods: {
    actionToggleChatView() {
      const curr = this.$store.state.selectedChat
      if (curr == null || curr.userId !== this.chat.userId) {
        this.$store.commit('selectChat', this.chat)
      } else {
        this.$store.commit('selectChat', null)
      }
    }
  }

}
</script>

<style lang="scss" scoped>

.main {
  border: 1px solid lightgray;
  border-radius: 10px;
  height: 40px;
  width: 200px;
  background-color: white;

  .title {
    text-align: center;
  }
}

.main:hover {
  cursor: pointer;
}

</style>
