import 'bootstrap/dist/css/bootstrap.min.css';

<template>
  <div class="container mt-5">
    <h2 class="mb-4 text-primary">Friends List</h2>

    <div class="form-row mb-3">
      <div class="col-md-3">
        <label for="userIdInput" class="text-secondary">User ID:</label>
        <input
          type="number"
          v-model="userIdInput"
          class="form-control"
          id="userIdInput"
        />
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
          <option value="nameStartsWithUnderscore">
            Starts with underscore
          </option>
          <option value="">Nothing</option>
        </select>
      </div>

      <div class="col-md-3">
        <label></label>
        <button @click="fetchFriends" class="btn btn-primary btn-block">
          Search
        </button>
      </div>
    </div>

    <div v-if="friends.length > 0">
      <h2 class="text-success">Friends:</h2>
      <ul class="list-group">
        <li
          v-for="friend in friends"
          :key="friend.id"
          class="list-group-item d-flex justify-content-between align-items-center"
        >
          {{ friend.name }}
          <button
            @click="removeFriend(friend.id)"
            class="btn btn-danger btn-sm"
          >
            Remove
          </button>
        </li>
      </ul>
    </div>
    

    <div v-else>
      <p v-if="userIdInput" class="text-danger">
        No friends found for the user with ID {{ userIdInput }}
      </p>
      <p v-else class="text-danger">
        No friends found. Please enter a valid user ID.
      </p>
    </div>

    <div class="form-row mb-3">
          <!-- Add buttons to trigger the new methods -->
          <div class="col-md-3">
            <button @click="fetchPendingInvitations" class="btn btn-primary btn-block">Fetch Pending Invitations</button>
          </div>
    
          <div class="col-md-3">
            <button @click="sendInvitation(2) " class="btn btn-primary btn-block">Send Invitation</button>
          </div>
    
          <div class="col-md-3">
            <button @click="acceptInvitation(1)" class="btn btn-success btn-block">Accept Invitation</button>
          </div>
    
          <div class="col-md-3">
            <button @click="declineInvitation(1)" class="btn btn-danger btn-block">Decline Invitation</button>
          </div>
        </div>
  </div>
</template>

<script>
export default {
  name: "FriendList",
  data() {
    return {
      friends: [],
      userIdInput: null,
      sortByInput: "name",
      filterByInput: "nameStartsWithUnderscore",
    };
  },
  methods: {
    fetchFriends() {
      if (!this.userIdInput) {
        console.error("Please enter a user ID");
        return;
      }

      const apiUrl = `http://localhost:8080/api/friends/users/get/${this.userIdInput}?sortBy=${this.sortByInput}&filterBy=${this.filterByInput}`;

      fetch(apiUrl, {
        method: "GET",
        credentials: "include",
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
          console.error("Fetch error:", error);
          this.friends = [];
        });
    },
    removeFriend(friendId) {
      console.log(`Removing friend with ID ${friendId}...`);
      if (!this.userIdInput) {
        console.error("Please enter a user ID");
        return;
      }
      console.log("Approaching apiURL");

      const apiUrl = `http://localhost:8080/api/friends/users/delete/${this.userIdInput}/${friendId}`;

      console.log("Approaching fetch");
      fetch(apiUrl, {
        method: "GET",
        credentials: "include",
      })
        .then((response) => {
          console.log("Approaching response");
          if (!response.ok) {
            console.log(response.status);
            throw new Error(`HTTP error! Status: ${response.status}`);
          }
          console.log("Friend removed successfully.");
          this.fetchFriends();
        })
        .catch((error) => {
          console.error("Fetch error:", error);
        });
    },
    fetchPendingInvitations() {
      if (!this.userIdInput) {
        console.error("Please enter a user ID");
        return;
      }

      const apiUrl = `http://localhost:8080/api/friends/invitations/pending/${this.userIdInput}`;

      fetch(apiUrl, {
        method: "GET",
        credentials: "include",
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
          }
          return response.json();
        })
        .then((data) => {
          console.log(data);
          // Handle the data as needed (e.g., display pending invitations)
        })
        .catch((error) => {
          console.error("Fetch error:", error);
        });
    },

    sendInvitation(recipientId) {
      if (!this.userIdInput) {
        console.error("Please enter a user ID");
        return;
      }

      const apiUrl = `http://localhost:8080/api/friends/invitations/send/${this.userIdInput}/${recipientId}`;

      fetch(apiUrl, {
        method: "GET",
        credentials: "include",
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
          }
          console.log("Invitation sent successfully.");
        })
        .catch((error) => {
          console.error("Fetch error:", error);
        });
    },

    acceptInvitation(invitationId) {
      const apiUrl = `http://localhost:8080/api/friends/invitations/accept/${invitationId}`;

      fetch(apiUrl, {
        method: "GET",
        credentials: "include",
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
          }
          console.log("Invitation accepted successfully.");
        })
        .catch((error) => {
          console.error("Fetch error:", error);
        });
    },

    declineInvitation(invitationId) {
      const apiUrl = `http://localhost:8080/api/friends/invitations/decline/${invitationId}`;

      fetch(apiUrl, {
        method: "GET",
        credentials: "include",
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
          }
          console.log("Invitation declined successfully.");
        })
        .catch((error) => {
          console.error("Fetch error:", error);
        });
    },
  },
};
</script>

<style scoped></style>
