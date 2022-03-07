<template>
 <div class="main" v-show="isShowing">
 </div>
</template>

<script>
export default {
  name: 'UserInfoModal',
  data() {
    return {
      isShowing: false
    }
  },
  methods: {
    show() {
      // this.isShowing = true
    }
  }
}
</script>

<style lang="scss" scoped>

.main {
  position: absolute;
  width: 300px;
  height: 300px;
}

</style>
