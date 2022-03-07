<template>
  <div ref="main" class="main">
    <div class="container" v-for="(file, idx) in files" :key="idx" >
      <span class="delete" @click="actionDelete(idx)">X</span>
      <img :src="file" alt="image">
    </div>
    <div class="no-picture" v-if="files.length === 0">
      <span>No picture</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ImageContainer',
  props: ['files'],
  methods: {
    actionDelete(idx) {
      this.$emit('deleteImage', idx)
    }
  }
}
</script>

<style lang="scss" scoped>

.main {
  width: 60%;
  height: 180px;
  overflow: auto;
  white-space: nowrap;

  .container {
    height: 100%;
    display: inline-block;

    img {
      height: 100%;
    }

    span:hover {
      cursor: pointer;
    }
  }

  .no-picture {
    display: inline-block;
    height: 100%;
    width: 200px;
    background-color: gray;

    span {
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100%;
      width: 100%;
      font-size: 16px;
      font-weight: bold;
      color: white;
    }
  }
}

</style>
