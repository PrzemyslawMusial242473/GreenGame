<!-- FriendList.vue -->
<template>
  <div>
    <h2>Friends List</h2>
    <label for="userIdInput">User ID:</label>
    <input type="number" v-model="userIdInput" id="userIdInput" />
    <button @click="fetchFriends">Search</button>
    <ul>
      <li v-for="friend in friends" :key="friend.id">{{ friend.name }}</li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'FriendList',
  data() {
    return {
      friends: [],
      userIdInput: null,
    };
  },
  methods: {
    fetchFriends() {
      if (!this.userIdInput) {
        console.error('Please enter a user ID');
        return;
      }

      const apiUrl = `http://localhost:8080/api/friends/users/${this.userIdInput}/friends`;

      fetch(apiUrl, {
        method: 'GET',
        credentials: 'include', 
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
          }
          return response.json();
        })
        .then((data) => {
          console.log(data);
          this.friends = data.friends;
        })
        .catch((error) => {
          console.error('Fetch error:', error);
        });
    },
  },
};
</script>

<style scoped>
</style>
