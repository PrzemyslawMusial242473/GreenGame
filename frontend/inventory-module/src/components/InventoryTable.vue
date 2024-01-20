<template>
  <div class="background-container">
  <div>
    <h1 class="inventory">INVENTORY</h1>
    <button class="button" :style="{ 'background-color': '#3498db' }" @click="goBack()"> Go back </button>
    <button class="button" :style="{ 'background-color': '#da7c65' }" @click="fetchItems()"> Refresh </button>
    <button class="button" :style="{ 'background-color': '#e1c227' }" @click="addItem()"> Test add item </button>
    <table>
      <thead>
        <tr class="table_header">
          <th v-for="(column, columnIndex) in columns" :key="columnIndex">
            {{ column }}
          </th>
        </tr>
      </thead>
      <draggable :list="list" v-model="inventory.items" tag="tbody">
        <template #item="{ element: item, index }">
          <tr :style="{ backgroundColor: rowColor(index) }">
            <td class="roboto-font">{{ item.id }} / {{ index }}</td> 
            <td class="roboto-font">{{ item.name }}</td> 
            <td style="width: 250px;" class="roboto-font">{{ item.description }}</td>
            <td class="roboto-font">{{ item.value }}</td> 
            <td style="width: 80px;"><button class="button-table" :style="{ 'background-color': '#ff0000' }" @click="deleteItem(item.id)">Delete</button></td>
            <td style="width: 80px;"><button class="button-table" :style="{ 'background-color': '#008000' }" @click="sellItem(item.id, index)">Sell</button></td>
          </tr>
        </template>
      </draggable>
    </table>
  </div>
  <div class="money">Inventory value: {{ inventoryValue.toFixed(2) }}</div>
  <div class="money"> Balance: {{ this.inventory.balance.toFixed(2) }}</div>
  </div>
</template>

<script>
import draggable from 'vuedraggable';
import axios from '../axios.js'

export default {

  name: "InventoryTable",
  components: {
    draggable,
  },
  
  data() {
    return {
      columns: ['ItemID/Index', 'Name', 'Description', 'Value', 'Delete', 'Sell'],
      columnWidths: [150, 150, 150, 150, 100, 100],
      items: [],

      inventory: {
          id: 1,
          userId: 1,
          items: this.items,
          balance: 50,
          nextFreeSlotIndex: 0,
          inventoryValue: 0,
      },

      helloPage: "/localhost:8081/secured/hello",
      
      /// DO ZMIANY, TRZEBA POZYSKAC TO ZKONDS ALBO ZMIENIC API ZEBY PRZYDZIALO SAMO TO USER ID
      userID: null,
      /// !
      inventoryValue: 0,
    };
  },


  beforeMount() {
    this.assignUser();  // tymczasowo, teoretycznie to powinno sie odbywaC tuz po zalozeniu konta
    this.fetchItems();
    this.getLoggedUserID();
  },

  methods: {
    goBack() {
      // TODO: noo powrot do home czy cos takiego
    },

    getLoggedUserID() {
      axios.get('/userID', {withCredentials: true})
      .then(response => {
        this.userID = response.data;
      })
    },

    assignUser() {
    axios.post(`/assignUser`, null, { withCredentials: true })
    .then(response => { 
      console.log(response);
      console.log(response.data);
      if (response.data == true) {console.log("assigned");}
      else {console.log("not assigned");}
      })
    .catch(error => { console.error(error); });
    },
    
    fetchItems() {
      axios.get(`/getUserInventory`, { withCredentials: true }) 
      .then(response => {
        console.log(response.data);
        this.inventory = response.data;
        // var numberOfItems = this.inventory.items.length;
        // for (var i=0;i<10-numberOfItems;i++) {
        //   this.items.push({ id: null, name: null, description: null, value: null })
        // }
        this.inventoryValue = this.getInventoryValue();
      })
      .catch(error => { console.log(error); });
      
    },

    modifyBalance(balanceDifference) { // to sell or delete items
      axios.post(`/modifyBalance/${this.userID}/${balanceDifference}`, null, { withCredentials: true })
      .then(response => { console.log(response.data); })
      .catch(error => { console.error(error); });
    },

    getInventoryValue() {
      var invValue = 0;
      if (this.inventory.items != undefined) {
      for(var i=0;i<this.inventory.items.length;i++) { invValue += this.inventory.items[i].value; } }
      return invValue;
    },
    
    addItem() { // TEST FUNCTION
    var random1 = Math.floor(Math.random() * 100);
    var random2 = Math.floor(Math.random() * 1000) / 10;
    var itemToAdd = {
        id: random1, name: 'Knife' + random1, description: 'Karambit? Noooo. It would be really long description.', value: random2,
    };
    axios.post(`/addItem/${this.userID}`, itemToAdd, { withCredentials: true })
      .then(response => { 
        console.log(response.data); 
        this.fetchItems();
        })
      .catch(error => { console.error(error); });
      
    },

    deleteItem(itemID) {
      axios.delete(`/deleteItemFromInventory/${this.userID}/${itemID}`, null, { withCredentials: true })
      .then(response => { 
        console.log(response.data); 
        this.fetchItems(); 
        })
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

.table_header {
  margin-left: 20px;
  /* margin-right: 20px; */
  /* border-collapse: collapse; */
}

table, th, td {
  border: 1px solid #ddd;
}

td {
  width: 150px;
  padding: 8px;
  text-align: center;
}

th {
  background-color: #f2f2f2;
}

.button {
      margin-left:20px;
      padding: 10px;
      color: #fff;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      margin-bottom:5px;
}

.button-table {
      padding: 10px;
      color: #fff;
      border: none;
      border-radius: 5px;
      cursor: pointer;
}

.background-container {
  background-image: url('~@/assets/inventory.png');
  background-size: cover;
  background-position: left;
  background-repeat: repeat;
  top: 0px;
  left: 0px;
  width: 100%;
  height: 1000px;
  margin-top: 0;
}

.inventory {
  font-family: 'Roboto', sans-serif;
  font-size:50px;
  margin-left:20px;
  margin-top:30px;
  color:green;
}

.money {
  font-family: 'Roboto', sans-serif;
  font-size:24px;
  color:blue;
  margin-top: 15px;
  margin-left: 20px;
}

</style>
