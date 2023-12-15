<template>
  <div>
    <h2>Friend List</h2>
    <ul>
      <li v-for="friend in friends" :key="friend.id">
        {{ friend.name }} ({{ friend.email }})
        <button @click="deleteFriend(friend.id)">Delete</button>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      friends: [],
    };
  },
  mounted() {
    // Fetch the list of friends when the component is mounted
    this.fetchFriends();
  },
  methods: {
    fetchFriends() {
      // Make an HTTP request to fetch friends from the Spring Boot backend
      // Update the URL with your actual backend endpoint
      fetch('http://localhost:8080/api/friends/1') // Assuming user ID is 1
          .then(response => response.json())
          .then(data => {
            this.friends = data;
          })
          .catch(error => {
            console.error('Error fetching friends', error);
          });
    },
    deleteFriend(friendId) {
      // Make an HTTP request to delete a friend by ID
      // Update the URL with your actual backend endpoint
      fetch(`http://localhost:8080/api/friends/${friendId}`, {
        method: 'DELETE',
      })
          .then(() => {
            // Refresh the friend list after deletion
            this.fetchFriends();
          })
          .catch(error => {
            console.error('Error deleting friend', error);
          });
    },
  },
};
</script>

