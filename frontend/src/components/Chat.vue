<template>
  <div class="blurred-background">
    <div class="container mt-5">
      <h2 class="mb-4 text-primary">Chat Application</h2>

      <!-- Chat history -->
      <div class="row mb-4">
        <div class="col-md-6">
          <label for="senderInput" class="form-label">Sender ID:</label>
          <input
            type="number"
            v-model="senderId"
            class="form-control"
            id="senderInput"
          />
        </div>
        <div class="col-md-6">
          <label for="receiverInput" class="form-label">Receiver ID:</label>
          <input
            type="number"
            v-model="receiverId"
            class="form-control"
            id="receiverInput"
          />
        </div>
        <div class="col-12 mt-3">
          <button @click="fetchChatHistory" class="btn btn-primary">
            Fetch Chat History
          </button>
        </div>
      </div>

      <!-- Display chat messages -->
      <div v-if="chatMessages.length > 0" class="mt-4">
        <h2 class="text-success mb-3">Chat History:</h2>
        <ul class="list-group">
          <li
            v-for="message in chatMessages"
            :key="message.id"
            class="list-group-item"
          >
            {{ message.senderId }}: {{ message.content }}
          </li>
        </ul>
      </div>

      <!-- Send message form -->
      <div class="mt-4">
        <h2 class="text-primary mb-3">Send a Message</h2>
        <input
          type="text"
          v-model="newMessage"
          class="form-control mb-2"
          placeholder="Type your message here..."
        />
        <button @click="sendMessage" class="btn btn-success">
          Send Message
        </button>
      </div>

      <!-- Additional features (Block/Unblock, Check unread messages, etc.) -->
      <!-- Similar to your friend list code, implement these features as needed -->
    </div>
  </div>
</template>

<script>
export default {
  name: "ChatApp",
  data() {
    return {
      senderId: null,
      receiverId: null,
      chatMessages: [],
      newMessage: "",
    };
  },
  methods: {
    fetchChatHistory() {
      const apiUrl = `http://localhost:8080/secured/api/chat/history?sender=${this.senderId}&receiver=${this.receiverId}`;
      fetch(apiUrl, {
        method: "GET",
        credentials: "include",
      })
        .then((response) => response.json())
        .then((data) => {
          this.chatMessages = data;
        })
        .catch((error) => console.error("Fetch error:", error));
    },
    sendMessage() {
      const apiUrl = `http://localhost:8080/secured/api/chat/send`;
      const message = {
        senderId: this.senderId,
        receiverId: this.receiverId,
        content: this.newMessage,
      };
      fetch(apiUrl, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(message),
        credentials: "include",
      })
        .then((response) => response.text())
        .then((data) => {
          console.log(data);
          this.newMessage = "";
          this.fetchChatHistory();
        })
        .catch((error) => console.error("Send message error:", error));
    },
    // Add methods for block/unblock, checking unread messages, etc. as needed
  },
};
</script>

<style scoped>
.blurred-background {
  /* Your existing styling */
}
</style>
