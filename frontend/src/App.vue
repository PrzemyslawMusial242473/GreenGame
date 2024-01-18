<!-- App.vue -->
<template>
  <div id="app">
    <!-- <FriendList /> -->
    <ScoreboardTable />
  </div>
</template>

<script>
import 'bootstrap/dist/css/bootstrap.min.css';
import FriendList from './components/FriendList.vue';
import ScoreboardTable from './components/ScoreboardTable.vue';

export default {
  components: {
    FriendList,
    ScoreboardTable
},
};
</script>

<style>
/* Add your global styles here */
</style>
