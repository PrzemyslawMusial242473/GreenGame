<template>
  <div class="background-container">
  <div>
    <button class="go-back-button" @click="goBack()">
    <i class="fas fa-sync-alt"></i> Go back
    </button>
    <h1 class="inventory">INVENTORY</h1>
    <button class="refresh-button" @click="fetchItems()">
    <i class="fas fa-sync-alt"></i> Refresh
    </button>
    <draggable :list="list" v-model="inventory.items" tag="table" @end="moveItems()">
      <template #item="{ element: item, index }">
        <tr :style="{ backgroundColor: rowColor(index) }">
          <td class="roboto-font">{{ item.id }} / {{ index }}</td> 
          <td class="roboto-font">{{ item.name }}</td> 
          <td class="roboto-font">{{ item.description }}</td>
          <td class="roboto-font">{{ item.value }}</td> 
          <td>
            <button @click="deleteItem(item.id)">Delete</button>
            <button @click="sellItem(item.id, index)">Sell</button>  
          </td>
        </tr>
      </template>
    </draggable>
    <button class="refresh-button" @click="addItem()">
      <i class="fas fa-sync-alt"></i> Test add item
    </button>
  </div>
  
  <div class="money">Inventory value: {{ getInventoryValue() }}</div>
  <div class="money"> Balance: {{ this.inventory.balance }}</div>
  </div>
</template>

<script>
// import backgroundImage from '@/assets/inventory.png';
import draggable from 'vuedraggable';
import axios from '../axios.js'

export default {

  name: "InventoryTable",
  components: {
    draggable,
  },
  
  data() {
    return {
      items: [],

      inventory: {
          id: 1,
          userId: 1,
          items: this.items,
          balance: 50,
          nextFreeSlotIndex: 0,
          inventoryValue: 0,
      },

      baseURL: "http://localhost:8080/api/inventory",
      helloPage: "/localhost:8081/secured/hello",
      userID: 1,
      balance: 200,
    };
  },


  beforeMount() {
    this.assignUser();  // tymczasowo, teoretycznie to powinno sie odbywaC tuz po zalozeniu konta
    this.fetchItems();
  },

  methods: {
    goBack() {
      // TODO: noo powrot do home czy cos takiego
    },

    addItem() { // TEST FUNCTION
    var random1 = Math.floor(Math.random() * 100);
    var random2 = Math.floor(Math.random() * 1000) / 10;
    var itemToAdd = {
        id: random1, name: 'Knife' + random1, description: 'Karambit', value: random2,
    };
    axios.post(`/addItem/${this.userID}`, itemToAdd, { withCredentials: true })
      .then(response => { console.log(response.data); this.fetchItems();})
      .catch(error => { console.error(error); });
    },

    assignUser() {
    axios.post(`/assignUser`, null, { withCredentials: true })
    .then(response => { 
      console.log(response);
      console.log(response.data);
      if (response.data == true) {console.log("assigned");}
      else {console.log("not assigned :(");}
      })
    .catch(error => { console.error(error); });
    },

    // addItemToInventory (item) {
    //   axios.post(`/additem/${this.userID}`, item, { withCredentials: true })
    //   .then(response => { console.log(response.data); })
    //   .catch(error => { console.error(error); });
    // },
    
    fetchItems() {
      axios.get(`/getUserInventory`, { withCredentials: true }) 
      .then(response => {
        console.log(response.data);
        this.inventory = response.data;
        var numberOfItems = this.inventory.items.length;
        for (var i=0;i<10-numberOfItems;i++) {
          this.items.push({ id: null, name: null, description: null, value: null })
        }
      })
      .catch(error => { console.log(error); });
    },

    moveItems(event) {
      const oldIndex = event.newIndex;
      const newIndex = event.oldIndex;
      axios.post(`/moveItems/${this.userID}/${oldIndex}/${newIndex}`, null, { withCredentials: true })
      .then(response => { console.log(response.data); })
      .catch(error => { console.error(error); });
    },

    modifyBalance(balanceDifference) { // to sell or delete items
      axios.post(`/modifyBalance/${this.userID}/${balanceDifference}`, null, { withCredentials: true })
      .then(response => { console.log(response.data); })
      .catch(error => { console.error(error); });
    },

    getInventoryValue() {
      var invValue = 0;
      for(var i=0;i<this.items.length;i++) { invValue += this.items[i].value; }
      return invValue;
    },

    deleteItem(itemID) {
      axios.delete(`/deleteItemFromInventory/${this.userID}/${itemID}`, null, { withCredentials: true })
      .then(response => { console.log(response.data); this.fetchItems(); })
      .catch(error => { console.error(error); });
    },

    sellItem(itemID, index) {
      this.modifyBalance(this.inventory.items[index].value)
      this.deleteItem(itemID)
    },

    // getItemFromRow jest useless bo mamy wszystkie itemy juz pobrane wiec to mozna pobrac tu
    //    wiec to bardziej do jakichs tam innych co itemy biora
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

.go-back-button {
      margin-left:20px;
      margin-top:20px;
      padding: 10px;
      background-color: #3498db;
      color: #fff;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      margin-bottom:5px;
}

.background-container {
  /* Set background properties */
  background-image: url('~@/assets/inventory.png');
  background-size: cover;
  background-position: left;
  background-repeat: repeat;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

.inventory {
  font-family: 'Roboto', sans-serif;
  font-size:50px;
  text-align:center;
  color:green;
}

.money {
  font-family: 'Roboto', sans-serif;
  font-size:30px;
  text-align:center;
  color:blue;
  margin-top: 5px;
}

</style>
