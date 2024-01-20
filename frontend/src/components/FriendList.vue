import 'bootstrap/dist/css/bootstrap.min.css';

<template>
  <div class="blurred-background">
    <div class="container mt-5">
      <h2 class="mb-4 text-primary">Friends List</h2>

      <div class="row mb-4">
        <div class="col-md-4">
          <label for="sortByInput" class="form-label">Sort By:</label>
          <select v-model="sortByInput" class="form-select" id="sortByInput">
            <option value="name">Name</option>
            <option value="reverseName">Reverse name</option>
            <option value="nameLength">Length of name</option>
          </select>
        </div>

        <div class="col-md-4">
          <label for="filterByInput" class="form-label">Filter By:</label>
          <select
            v-model="filterByInput"
            class="form-select"
            id="filterByInput"
          >
            <option value="nameStartsWithUnderscore">
              Starts with underscore
            </option>
            <option value="">Nothing</option>
          </select>
        </div>

        <div class="col-md-4 d-flex align-items-end">
          <button @click="fetchFriends" class="btn btn-primary w-100">
            Search/fetch
          </button>
        </div>
      </div>

      <div v-if="friends.length === 0" class="mt-4 text-center">
        <h3 class="text-secondary">No friends</h3>
      </div>
      <div v-if="friends.length > 0" class="mt-4">
        <h2 class="text-success mb-3">Friends:</h2>
        <ul class="list-group">
          <li
            v-for="friend in friends"
            :key="friend.id"
            class="list-group-item"
          >
            <div class="d-flex justify-content-between align-items-center">
              {{ friend.name }}
              <button
                @click="removeFriend(friend.id)"
                class="btn btn-sm btn-outline-danger"
              >
                Remove
              </button>
            </div>
          </li>
        </ul>
      </div>

      <button
        @click="toggleUsersDisplay()"
        class="btn btn-outline-primary mb-3 w-100"
      >
        {{ showAllUsers ? "Hide Users" : "Show All Users" }}
      </button>

      <div v-if="showAllUsers && allUsers.length > 0">
        <h2 class="text-success mb-3">All Users:</h2>
        <ul class="list-group">
          <li v-for="user in allUsers" :key="user.id" class="list-group-item">
            <div class="d-flex justify-content-between align-items-center">
              {{ user.name }}
              <button
                v-if="!isUserFriend(user.id)"
                @click="sendInvitation(user.id)"
                class="btn btn-sm btn-outline-primary"
              >
                Send Invite
              </button>
              <span v-else class="text-muted">Already a friend</span>
            </div>
          </li>
        </ul>
      </div>

      <button
        @click="toggleBlockedDisplay()"
        class="btn btn-outline-primary mb-3 w-100"
      >
        {{ showBlockedUsers ? "Hide blocked users" : "Show blocked users" }}
      </button>

      <div class="mt-4">
        <h2 class="text-danger mb-3">Blocked Users:</h2>
        <ul class="list-group" v-if="blockedUsers.length > 0">
          <li
            v-for="user in blockedUsers"
            :key="user.id"
            class="list-group-item"
          >
            {{ user.name }}
            <button
              @click="unblockUser(user.id)"
              class="btn btn-sm btn-outline-success float-right"
            >
              Unblock
            </button>
          </li>
        </ul>
        <div v-else class="text-center text-muted">No blocked users</div>
      </div>

      <div class="row mt-4">
        <div class="col-md-4">
          <button
            @click="fetchPendingInvitations"
            class="btn btn-primary w-100"
          >
            Fetch Pending Invitations
          </button>
        </div>
      </div>

      <div v-if="invitations && invitations.length > 0" class="mt-3">
        <h3 class="mb-2">Pending Invitations</h3>
        <ul class="list-group">
          <li
            v-for="invitation in invitations"
            :key="invitation.id"
            class="list-group-item"
          >
            <div class="d-flex justify-content-between align-items-center">
              Invitation from User ID: {{ invitation.senderId }}
              <div class="btn-group">
                <button
                  @click="acceptInvitation(invitation.id)"
                  class="btn btn-sm btn-success"
                >
                  Accept
                </button>
                <button
                  @click="declineInvitation(invitation.id)"
                  class="btn btn-sm btn-warning"
                >
                  Decline
                </button>
                <button
                  @click="blockUser(invitation.senderId)"
                  class="btn btn-sm btn-danger"
                >
                  Block
                </button>
              </div>
            </div>
          </li>
        </ul>
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
      allUsers: [],
      invitations: [],
      blockedUsers: [],
      userIdInput: null,
      sortByInput: "name",
      filterByInput: "",
      htmlContent: "",
      showAllUsers: false,
      showBlockedUsers: false,
    };
  },
  methods: {
    fetchFriends() {
      const apiUrl = `http://localhost:8080/secured/api/friends/users/get?sortBy=${this.sortByInput}&filterBy=${this.filterByInput}`;
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
    getAllUsers: function () {
      this.showAllUsers = true;
      const apiUrl = `http://localhost:8080/secured/api/friends/users/get/allusers`;
      fetch(apiUrl, {
        method: "GET",
        credentials: "include",
      })
        .then((response) => {
          if (response.status === 403) {
            window.location.href = "localhost:8000/login";
            return;
          }
          if (response.redirected && response.url.includes("login")) {
            console.log("Redirected to login page:", response.url);
            window.location.href = response.url;
            return Promise.reject(new Error("Redirected to login"));
          }
          return response.json();
        })
        .then((data) => {
          this.allUsers = data;
        })
        .catch((error) => {
          console.error("Fetch error:", error);
        });
    },
    removeFriend(friendId) {
      console.log(`Removing friend with ID ${friendId}...`);
      console.log("Approaching apiURL");

      const apiUrl = `http://localhost:8080/api/friends/users/delete/${friendId}`;

      console.log("Approaching fetch");
      fetch(apiUrl, {
        method: "GET",
        credentials: "include",
      }).then((response) => {
        if (response.redirected && response.url.includes("login")) {
          console.log("Redirected to login page:", response.url);
          window.location.href = response.url;
          return Promise.reject(new Error("Redirected to login"));
        }
        if (!response.ok) {
          console.log(response.status);
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        console.log("Friend removed successfully.");
        this.fetchFriends();
      });
    },
    fetchPendingInvitations() {
      const apiUrl = `http://localhost:8080/secured/api/friends/invitations/pending`;

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
          this.invitations = data;
        })
        .catch((error) => {
          console.error("Fetch error:", error);
        });
    },
    sendInvitation(recipientId) {
      if (this.isUserBlocked(recipientId)) {
        alert("Cannot send invitation to a blocked user.");
        return;
      }

      const apiUrl = `http://localhost:8080/secured/api/friends/invitations/send/${recipientId}`;

      fetch(apiUrl, {
        method: "GET",
        credentials: "include",
      })
        .then((response) => {
          if (!response.ok) {
            alert(
              "Error sending invitation. User doesn't exist or you tried to send it to yourself."
            );
            throw new Error(`HTTP error! Status: ${response.status}`);
          }
          console.log("Invitation sent successfully.");
        })
        .catch((error) => {
          console.error("Fetch error:", error);
        });
      this.fetchPendingInvitations();
    },
    acceptInvitation(invitationId) {
      const apiUrl = `http://localhost:8080/secured/api/friends/invitations/accept/${invitationId}`;

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
      this.fetchPendingInvitations();
    },
    declineInvitation(invitationId) {
      const apiUrl = `http://localhost:8080/secured/api/friends/invitations/decline/${invitationId}`;

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
      this.fetchPendingInvitations();
    },
    async fetchUserId() {
      try {
        const response = await fetch(
          "http://localhost:8080/secured/api/friends/users/get/test",
          {
            method: "GET",
            credentials: "include",
          }
        );

        console.log(response);

        if (response.redirected && response.url.includes("login")) {
          console.log("Redirected to login page:", response.url);
          window.location.href = response.url;
          return;
        }

        const contentType = response.headers.get("content-type");
        console.log("Content-Type:", contentType);

        if (contentType && contentType.includes("application/json")) {
          const data = await response.json();
          console.log("User data:", data);
        } else {
          const textData = await response.text();
          console.log("Response text:", textData);
        }
      } catch (error) {
        console.error("Fetch error:", error);
      }
    },
    toggleUsersDisplay() {
      this.showAllUsers = !this.showAllUsers;
      if (this.showAllUsers) {
        this.getAllUsers();
      }
    },
    toggleBlockedDisplay() {
      this.showBlockedUsers = !this.showBlockedUsers;
      if (this.showBlockedUsers) {
        this.fetchBlockedUsers();
      }
    },
    isUserFriend(userId) {
      return this.friends.some((friend) => friend.id === userId);
    },
    isUserBlocked(userId) {
      return this.blockedUsers.some((user) => user.id === userId);
    },
    blockUser(blockeeId) {
      const apiUrl = `http://localhost:8080/secured/api/friends/users/block/${blockeeId}`;

      fetch(apiUrl, {
        method: "GET",
        credentials: "include",
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
          }
          console.log("User blocked successfully.");
        })
        .catch((error) => {
          console.error("Fetch error:", error);
        });
    },
    unblockUser(blockeeId) {
      const apiUrl = `http://localhost:8080/secured/api/friends/users/unblock/${blockeeId}`;

      fetch(apiUrl, {
        method: "GET",
        credentials: "include",
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
          }
          console.log("User unblocked successfully.");
        })
        .catch((error) => {
          console.error("Fetch error:", error);
        });
    },
    fetchBlockedUsers() {
      const apiUrl = `http://localhost:8080/secured/api/friends/users/get/blocked`;

      fetch(apiUrl, {
        method: "GET",
        credentials: "include",
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
          }
          return response.text();
        })
        .then((text) => {
          if (text) {
            const data = JSON.parse(text);
            console.log("Response JSON:", data);
            this.blockedUsers = data;
            console.log("Blocked users:", this.blockedUsers);
          } else {
            console.log("No content in the response");
            this.blockedUsers = [];
          }
        })
        .catch((error) => {
          console.error("Error fetching blocked users:", error);
        });
    },
  },
  mounted() {
    this.fetchFriends();
    this.fetchBlockedUsers();
  },
};
</script>

<style scoped>
.blurred-background {
  background-image: url("@/assets/forest.jpg");
  background-size: cover;
  background-position: center;
  width: 100%;
  height: 100vh;
  position: fixed;
  top: 0;
  left: 0;
  z-index: -1;
}
</style>
