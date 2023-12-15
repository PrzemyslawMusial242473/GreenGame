<template>
  <div id="app">
    <FriendList />
  </div>
</template>

<script>
import FriendList from './components/FriendList.vue'

export default {
  name: 'App',
  components: {
    FriendList
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
