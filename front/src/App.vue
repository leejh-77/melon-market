<template>
  <div>
    <MainHeader class="main-header"/>
    <router-view/>
  </div>
</template>

<script>
import MainHeader from './views/MainHeader.vue'

export default {
  components: {
    MainHeader
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

.main-header {
  padding: 15px 20%;
}

</style>
