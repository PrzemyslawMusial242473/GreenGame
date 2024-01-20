<template>
  <div>
    <div>
      <h2>List of users to talk with:</h2>
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
      <h2>Chat with {{ findFriendById(selectedFriend).name }}</h2>
      <div class="chat-history">
        <p v-for="message in chatHistory" :key="message.id">
          <strong>
            {{
              findFriendById(message.senderId).name ===
              findFriendById(selectedFriend).name
                ? findFriendById(selectedFriend).name
                : "you"
            }}:
          </strong>
          {{ message.content }}
        </p>
      </div>

      <div class="message-container">
        <textarea
          class="message-input"
          v-model="newMessage"
          placeholder="Type a message..."
        ></textarea>
        <button class="send-button" @click="sendMessage">Send</button>
      </div>
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
    };
  },
  methods: {
    fetchFriends() {
      const apiUrl = `http://localhost:8080/secured/api/friends/users/get`;
      fetch(apiUrl, {
        method: "GET",
        credentials: "include",
      })
        .then((response) => {
          if (response.redirected && response.url.includes("login")) {
            console.log("Redirected to login page:", response.url);
            window.location.href = response.url;
            return Promise.reject(new Error("Redirected to login"));
          }
          return response.json();
        })
        .then((data) => {
          this.friends = data.friends;
        })
        .catch((error) => {
          console.error("Fetch error:", error);
          this.friends = [];
        });
    },
    findFriendById(id) {
      const friend = this.friends.find((friend) => friend.id === id);
      if (friend) {
        return friend;
      } else {
        return { name: "Unknown" };
      }
    },
    fetchChatHistory(friendId) {
      fetch(
        `http://localhost:8080/secured/api/chat/history?receiver=${friendId}`,
        {
          method: "GET",
          credentials: "include",
        }
      )
        .then((response) => {
          if (response.redirected && response.url.includes("login")) {
            console.log("Redirected to login page:", response.url);
            window.location.href = response.url;
            return Promise.reject(new Error("Redirected to login"));
          }
          return response.json();
        })
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
          if (response.redirected && response.url.includes("login")) {
            console.log("Redirected to login page:", response.url);
            window.location.href = response.url;
            return Promise.reject(new Error("Redirected to login"));
          }
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
.message-input {
  width: 100%;
  min-height: 50px;
  box-sizing: border-box;
  resize: vertical;
}

.send-button {
  height: 50px;
}

.message-container {
  display: flex;
  gap: 10px;
}
</style>
