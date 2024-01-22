<template>
    <div v-if="showPopup">
        <div id="daily-reward-pop-up">
            <h1>Daily Challenge</h1>
            <p>Would you like to start the daily challenge?</p>
            <button @click="startChallenge">Start</button>
            <button @click="later">Later</button>
        </div>
    </div>
</template>

<script>
export default {
  name: "DailyRewardPopUp",
  data() {
    return {
      showPopup: true,
    };
  },
  methods: {
    startChallenge() {
        
      this.showPopup = false;

    },

    later() {
      this.showPopup = false;
      // Set a timer to show the pop-up again after 5 minutes
      setTimeout(() => {
        this.showPopup = true;
      }, 300000);
    },
  },
};
</script>

<style scoped>
    #daily-reward-pop-up {
        top: 50%;
        left: 50%;
        position: fixed;
        transform: translate(-50%, -50%);
        padding: 20px;
        background-color: #f8f8f8;
        border: 1px solid #ccc;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        text-align: center;
        border-radius: 10px;
    }

    h1 {
        color: #333;
        font-size: 24px;
    }

    button {
        margin: 10px;
        padding: 10px 20px;
        font-size: 16px;
        cursor: pointer;
    }
</style>
