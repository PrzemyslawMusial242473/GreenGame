<!-- App.vue -->
<template>
  <div id="app">
    <FriendList />
  </div>
</template>

<script>
import 'bootstrap/dist/css/bootstrap.min.css';
import FriendList from './components/FriendList.vue';

export default {
  components: {
    FriendList,
  },
};
</script>

<style>
/* Add your global styles here */
</style>
