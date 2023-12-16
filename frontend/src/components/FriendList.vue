<!-- FriendList.vue -->
<template>
  <div>
    <h2>Friends List</h2>
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
    };
  },
  mounted() {
    const apiUrl = 'http://localhost:8080/api/friends/users/1/friends';

    fetch(apiUrl, {
      method: 'GET',
      credentials: 'include', // Include credentials (cookies, HTTP authentication)
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
      })
      .then((data) => {
        console.log(data);
        // Assuming the response format is an array of friends
        this.friends = data.friends;
      })
      .catch((error) => {
        console.error('Fetch error:', error);
      });
  },
};
</script>

<style scoped>
/* Add your component styles here */
</style>
