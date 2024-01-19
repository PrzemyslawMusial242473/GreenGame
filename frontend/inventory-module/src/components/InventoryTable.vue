<template>
  <div>
    <h1>Inventory</h1>
    <button class="refresh-button" @click="fetchItems()">
      <div>ee</div>
    <button class="refresh-button2" @click="fetchFriends()"></button>
  <i class="fas fa-sync-alt"></i> Refresh
</button>
    <draggable :list="list" v-model="items" tag="table" @end="moveItems">
      <template #item="{ element: item, index }">
        <tr :style="{ backgroundColor: rowColor(index) }">
          <td class="roboto-font">{{ item.name }}</td> 
          <td class="roboto-font">{{ item.description }}</td>
          <td class="roboto-font">{{ item.value }}</td> 
          <td>
            <button @click="deleteItem(index)">Delete</button>
            <button @click="sellItem(index)">Sell</button>
          </td>
        </tr>
      </template>
    </draggable>
  </div>

  <div class="invValue">Balance: {{ getInventoryValue() }}</div>

</template>

<script>
// import { ref } from 'vue';
import draggable from 'vuedraggable';
import axios from '../axios.js'

export default {

  name: "InventoryTable",
  components: {
    draggable,
  },
  
  data() {
    return {
      items: [
        { id: 1, name: 'Knife', description: 'Karambit', value: 5.00 },
        { id: 2, name: 'Pistol', description: 'Glock-18?', value: 420.00 },
        { id: 3, name: 'Bottle', description: 'Capacity: 0.75 l', value: 0.07 },
        { id: 4, name: 'Backpack', description: 'Capacity: 20', value: 45.00 },
        { id: null, name: null, description: null, value: null },
        { id: null, name: null, description: null, value: null },
        { id: null, name: null, description: null, value: null },
        { id: null, name: null, description: null, value: null },
        { id: null, name: null, description: null, value: null },
        { id: null, name: null, description: null, value: null },
      ],
      baseURL: "http://localhost:8080/api/inventory",

      userID: 1,
      balance: 200,
    };
  },


  beforeMount() {
    this.assignUser();  // tymczasowo, teoretycznie to powinno sie odbywaC tuz po zalozeniu konta
  },

  methods: {
    assignUser() {
    axios.post(`/assignUser/${this.userID}`, null, {
      withCredentials: true
    })
    .then(response => {
      // Handle the successful response
      console.log(response.data);
    })
    .catch(error => {
      // Handle errors
      console.error(error);
    });
    //   fetch(apiUrl, {
    //     method: "POST", withCredentials: true
    //   })
    //     .then((response) => {
    //       console.log(response);
    //       if (!response.ok) {
    //         throw new Error(`Error, status: ${response.status}`);
    //       }
    //       console.log("assigned");
    //     })
    //     .then((data) => {
    //       console.log(data);
    //     })
    //     .catch((error) => { console.error("Fetch error:", error); });
    },

    // addItemToInventory () {

    // },

    moveItems(event) {
      const oldIndex = event.newIndex;
      const newIndex = event.oldIndex;

      const apiUrl = this.baseURL + `/moveItems?userID=${this.userID}&index1=${oldIndex}&index2=${newIndex}`;

      console.log('Old Index:', oldIndex);
      console.log('New Index:', newIndex);

      fetch(apiUrl, {
        method: "POST", withCredentials: true
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error(`Error, status: ${response.status}`);
          }
        })
        .catch((error) => { console.error("Fetch error:", error); });
    },

    modifyBalance(balanceDifference) {
      const apiUrl = this.baseURL + `/modifyBalance?userID=${this.userID}&newBalance=${this.balance + balanceDifference}`;

      fetch(apiUrl, { method: "POST", withCredentials: true })
        .then((response) => {
          if (!response.ok) {
            throw new Error(`Error, status: ${response.status}`);
          }
        })
        .catch((error) => { console.error(error); });
    },

    getInventoryValue() {
      var invValue = 0;
      for(var i=0;i<10;i++) {
        invValue += this.items[i].value;
      }
      return invValue;
    },

    fetchItems() {
        axios.get(`/getUserInventory/${this.userID}`, 
        ) 
                .then(response => {
                    // console.log(response);
                    console.log(response.data);
                    // this.scores = response.data;
                })
                .catch(error => {
                    console.log(error);
                });
    },

    fetchFriends() {
        axios.get(`http://localhost:8080/secured/api/friends/users/get`, 
        ) 
                .then(response => {
                    // console.log(response);
                    console.log(response.data);
                    // this.scores = response.data;
                })
                .catch(error => {
                    console.log(error);
                });
    },


    deleteItem(index) {
      const apiUrl = this.baseURL + `/deleteItemFromSlot?userID=${this.userID}&index=${index}`;
      fetch(apiUrl, { method: "GET", withCredentials: true })
        .then((response) => {
          if (!response.ok) {
            console.log(response.status);
            throw new Error(`HTTP error! Status: ${response.status}`);
          }
          console.log("Item removed.");
          this.fetchItems();
        })
        .catch((error) => {
          console.error("Fetch error:", error);
        });
    },

    sellItem(index) {
      this.modifyBalance(this.items[index].value)
      this.deleteItem(index)
    },

    // getItemFromRow jest useless bo mamy wszystkie itemy juz pobrane wiec to mozna pobrac tu
    // getItemFromInventory to samo
    // deleteItemFromInventory tez bo mamy from slot a tu raczej na slotach latwiej
    // additem to chyba nie nasza rola
    // assignusertoinventory to chyba tez nie nasze ( a wgl to nie powinno byc assigninventorytouser? )


    rowColor (index) {
      return index % 2 === 0 ? 'lightblue' : 'lightgreen';
    },
  }
}

</script>

<style scoped>
@import url('https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap');
.roboto-font {
  font-family: 'Roboto', sans-serif;
}

table {
  margin-left: 20px;
  margin-right: 20px;
  border-collapse: collapse;
}

table, th, td {
  border: 1px solid #ddd;
}

td {
  width: 30%;
  padding: 8px;
  text-align: left;
}

th {
  background-color: #f2f2f2;
}

.invValue {
  margin-top: 20px;
  margin-left: 45%;
  
}

.refresh-button {
      margin-left:20px;
      padding: 10px;
      background-color: #3498db;
      color: #fff;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      margin-bottom:5px;
}

.refresh-button2 {
      margin-top:5px;
      margin-left:20px;
      padding: 10px;
      background-color: #c41291;
      color: #fff;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      margin-bottom:5px;
}
</style>
