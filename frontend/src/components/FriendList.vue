<!-- FriendList.vue -->
<template>
  <div>
    <h2>Friends List</h2>
    <label for="userIdInput">User ID:</label>
    <input type="number" v-model="userIdInput" id="userIdInput" />
    
    <label for="sortByInput">Sort By:</label>
    <select v-model="sortByInput" id="sortByInput">
      <option value="name">Name</option>
      <option value="reverseName">Reverse name</option>
      <option value="nameLength">Length of name</option>
    </select>
    
    <label for="filterByInput">Filter By:</label>
    <select v-model="filterByInput" id="filterByInput">
      <option value="nameStartsWithUnderscore">starts with underscore</option>
      <option value="">Nothing</option>
    </select>
    
    <button @click="fetchFriends">Search</button>
    <div v-if="friends.length > 0">
      <h2>Friends:</h2>
      <ul>
        <li v-for="friend in friends" :key="friend.id">{{ friend.name }}</li>
      </ul>
    </div>
    <div v-else>
      <p v-if="userIdInput">No friends found for the user with ID {{ userIdInput }}</p>
      <p v-else>No friends found. Please enter a valid user ID.</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'FriendList',
  data() {
    return {
      friends: [],
      userIdInput: null,
      sortByInput: 'name',
      filterByInput: 'nameStartsWithUnderscore',
    };
  },
  methods: {
    fetchFriends() {
      if (!this.userIdInput) {
        console.error('Please enter a user ID');
        return;
      }

      const apiUrl = `http://localhost:8080/api/friends/users/${this.userIdInput}/friends?sortBy=${this.sortByInput}&filterBy=${this.filterByInput}`;

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
          this.friends = [];
        });
    },
  },
};
</script>

<style scoped>
</style>
