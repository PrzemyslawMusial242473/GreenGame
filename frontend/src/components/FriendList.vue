import 'bootstrap/dist/css/bootstrap.min.css';

<template>
  <div class="container mt-5">
    <h2 class="mb-4 text-primary">Friends List</h2>

    <div class="form-row mb-3">
      <div class="col-md-3">
        <label for="userIdInput" class="text-secondary">User ID:</label>
        <input type="number" v-model="userIdInput" class="form-control" id="userIdInput" />
      </div>

      <div class="col-md-3">
        <label for="sortByInput" class="text-secondary">Sort By:</label>
        <select v-model="sortByInput" class="form-control" id="sortByInput">
          <option value="name">Name</option>
          <option value="reverseName">Reverse name</option>
          <option value="nameLength">Length of name</option>
        </select>
      </div>

      <div class="col-md-3">
        <label for="filterByInput" class="text-secondary">Filter By:</label>
        <select v-model="filterByInput" class="form-control" id="filterByInput">
          <option value="nameStartsWithUnderscore">Starts with underscore</option>
          <option value="">Nothing</option>
        </select>
      </div>

      <div class="col-md-3">
        <label></label>
        <button @click="fetchFriends" class="btn btn-primary btn-block">Search</button>
      </div>
    </div>

    <div v-if="friends.length > 0">
      <h2 class="text-success">Friends:</h2>
      <ul class="list-group">
        <li v-for="friend in friends" :key="friend.id" class="list-group-item d-flex justify-content-between align-items-center">
          {{ friend.name }}
          <button @click="removeFriend(friend.id)" class="btn btn-danger btn-sm">Remove</button>
        </li>
      </ul>
    </div>

    <div v-else>
      <p v-if="userIdInput" class="text-danger">No friends found for the user with ID {{ userIdInput }}</p>
      <p v-else class="text-danger">No friends found. Please enter a valid user ID.</p>
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

      const apiUrl = `http://localhost:8080/api/friends/users/get/${this.userIdInput}?sortBy=${this.sortByInput}&filterBy=${this.filterByInput}`;

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
    removeFriend(friendId) {
      console.log(`Removing friend with ID ${friendId}...`);
      if (!this.userIdInput) {
        console.error('Please enter a user ID');
        return;
      }
      console.log("Approaching apiURL");

      const apiUrl = `http://localhost:8080/api/friends/users/delete/${this.userIdInput}/${friendId}`;

      console.log("Approaching fetch");
      fetch(apiUrl, {
        method: 'GET',
        credentials: 'include', 
      })
        .then((response) => {
          console.log("Approaching response");
          if (!response.ok) {
            console.log(response.status);
            throw new Error(`HTTP error! Status: ${response.status}`);
          }
          console.log('Friend removed successfully.');
          this.fetchFriends();
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
