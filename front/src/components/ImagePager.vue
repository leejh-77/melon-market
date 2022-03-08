<template>
  <div class="main">
    <img @click="changeImage(-1)" class="arrow-image" src="../assets/left-arrow.png" alt="left arrow"/>
    <img class="main-image" alt="main image" :src="images[currentIndex]"/>
    <img @click="changeImage(1)" class="arrow-image" src="../assets/right-arrow.png" alt="right arrow"/>
  </div>
</template>

<script>
export default {
  name: 'ImagePager',
  props: ['images'],
  data() {
    return {
      currentIndex: 0
    }
  },
  methods: {
    changeImage(dir) {
      console.log(this.images.length)
      if (dir < 0 && this.currentIndex + dir >= 0) {
        this.currentIndex += dir
      }
      if (dir > 0 && this.currentIndex + dir < this.images.length) {
        this.currentIndex += dir
      }
    }
  }
}
</script>

<style lang="scss" scoped>

.main {
  display: flex;
  justify-content: center;
  height: 500px;

  .main-image {
    min-width: 100%;
    object-fit: cover;
    border-radius: 10px;
  }

  .arrow-image {
    width: 40px;
    height: 40px;
    align-self: center;
    padding: 5px;
  }

  .arrow-image:hover {
    cursor: pointer;
  }
}

</style>
