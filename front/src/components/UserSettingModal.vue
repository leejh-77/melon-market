<template>
  <div class="modal" v-show="isShowing">
    <button @click="$emit('writePost'); close()">Write Post</button>
    <button @click="$emit('changePicture'); close()">Change Picture</button>
    <button class="close-button" @click="close">Close</button>
  </div>
</template>

<script>
export default {
  name: 'UserSettingModal',
  data() {
    return {
      isShowing: false
    }
  },
  methods: {
    show() {
      this.isShowing = true
    },
    close() {
      this.isShowing = false
    }
  }
}
</script>

<style lang="scss" scoped>
.modal {
  position: absolute;
  display: flex;
  flex-direction: column;
  margin-top: 10px;
  width: 200px;
  border: 1px solid lightgray;
  background: white;
  border-radius: 10px;
  padding: 20px;

  button {
    width: 100%;
    height: 40px;
    font-size: 15px;
    border: 1px solid lightgray;
    border-radius: 10px;
    margin-bottom: 5px;
  }

  button:hover {
    cursor: pointer;
  }

  .close-button {
    width: 100%;
    height: 40px;
    font-size: 15px;
    border: 1px solid lightgray;
    background: white;
    border-radius: 10px;
    margin-bottom: 5px;
  }
}
</style>
