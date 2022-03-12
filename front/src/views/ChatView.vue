<template>
  <div class="main">
    <div ref="talk-scroll" class="talk-scroll">
      <div class="talk-container">
        <p v-for="message in messages" :key="message.id">{{ message.content }}</p>
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
      messages: [],
      inputText: ''
    }
  },
  methods: {
    actionSendMessage() {
      if (this.inputText.length === 0) {
        return
      }
      this.messages.push({
        id: 1,
        content: this.inputText
      })
      this.inputText = ''
      const el = this.$refs['talk-scroll']
      el.scrollTop = el.scrollHeight + 10
    }
  },
  mounted() {
    for (let i = 0; i < 10; i++) {
      this.messages.push({
        id: i,
        content: 'hi' + i
      })
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
