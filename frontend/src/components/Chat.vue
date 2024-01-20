<template>
  <div>
    <div>
      <h2>Friends List</h2>
      <ul>
        <li
          v-for="friend in friends"
          :key="friend.id"
          @click="selectFriend(friend.id)"
        >
          {{ friend.name }}
        </li>
      </ul>
    </div>

    <div v-if="selectedFriend">
      <h2>Chat with {{ selectedFriend }}</h2>
      <div class="chat-history">
        <p v-for="message in chatHistory" :key="message.id">
          <strong>{{ message.sender === userId ? "You" : "Friend" }}:</strong>
          {{ message.content }}
        </p>
      </div>

      <input v-model="newMessage" placeholder="Type a message..." />
      <button @click="sendMessage">Send</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "Chat",
  data() {
    return {
      friends: [],
      selectedFriend: null,
      chatHistory: [],
      newMessage: "",
      userId: 123, // Your user's ID
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
    fetchChatHistory(friendId) {
      fetch(
        `http://localhost:8080/secured/api/chat/history?receiver=${friendId}`,
        {
          method: "GET",
          credentials: "include",
        }
      )
        .then((response) => response.json())
        .then((data) => {
          this.chatHistory = data;
        });
    },
    selectFriend(friendId) {
      this.selectedFriend = friendId;
      this.fetchChatHistory(friendId);
    },
    sendMessage() {
      const params = new URLSearchParams();
      params.append("receiverId", this.selectedFriend);
      params.append("content", this.newMessage);

      fetch(
        `http://localhost:8080/secured/api/chat/send?${params.toString()}`,
        {
          method: "POST",
          credentials: "include",
          headers: {
            "Content-Type": "application/x-www-form-urlencoded",
          },
        }
      )
        .then((response) => {
          if (!response.ok) {
            throw new Error("Failed to send message");
          }
          this.fetchChatHistory(this.selectedFriend);
          this.newMessage = "";
        })
        .catch((error) => console.error("Send message error:", error));
    },
  },
  mounted() {
    this.fetchFriends();
  },
};
</script>

<style>
/* Add your CSS styling here */
</style>
