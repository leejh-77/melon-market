<template>
  <div>
    <MainHeader class="main-header"/>
    <router-view class="router-view"/>
    <MainFooter/>
  </div>
</template>

<script>
import MainHeader from './views/MainHeader.vue'
import MainFooter from '@/views/MainFooter'

export default {
  components: {
    MainFooter,
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

.router-view {
  min-height: 800px;
}

</style>
