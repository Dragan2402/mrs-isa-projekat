<template>
  <div class="main">
    <!-- <button class="btn btn-primary" @click="goToUserProfile()">User Profile</button>
    <button class="btn btn-primary" @click="goToFishingClassProfile()">Fishing Class Profile</button>
    <button class="btn btn-primary" @click="goToVacationHousesProfile()">Vacation House Profile</button> -->
    <br>
    <div class="entities-div">
      <div>
        <input class="inputSearch" type="text" placeholder="Filter by name, address..." v-model="filter"/>
      </div>
      <br>
      <div>
        <input class="radio" type="radio" id="contactChoice1"
               name="choice" value=0 v-model="displayType">
        <label for="contactChoice1"> Houses</label>
        <input class="radio" type="radio" id="contactChoice2"
               name="choice" value=1 v-model="displayType">
        <label for="contactChoice2">Ships</label>
        <input class="radio" type="radio" id="contactChoice3"
               name="choice" value=2 v-model="displayType">
        <label for="contactChoice3">Fishing Classes</label>
      </div>
      <br>
      <div v-if="this.displayType==0">
        <table>
          <thead>
          <th>Name</th>
          <th>Location</th>
          <th>Price</th>
          <th></th>
          </thead>
          <tbody>
          <tr v-for="(vacationHouse,index) in this.filteredVacationHouses" @Click="selectEntity(vacationHouse)"
              v-bind:index="index" :key="vacationHouse.id" v-bind="{selected: selectedEntity.id===vacationHouse.id}">
            <td>{{ vacationHouse.name }}</td>
            <td>{{ vacationHouse.address }}</td>
            <td>{{ vacationHouse.priceList }}</td>
            <td><label class="jump-link" @click="jumpToPreview(vacationHouse)">EXPLORE</label></td>
          </tr>
          </tbody>
        </table>
      </div>
      <div v-if="this.displayType==1">
        <table>
          <thead>
          <th>Name</th>
          <th>Location</th>
          <th>Price</th>
          </thead>
          <tbody>
          <tr v-for="(ship,index) in this.filteredShips" @Click="selectEntity(ship)" v-bind:index="index" :key="ship.id"
              v-bind="{selected: selectedEntity.id===ship.id}">
            <td>{{ ship.name }}</td>
            <td>{{ ship.address }}</td>
            <td>{{ ship.priceList }}</td>
            <td><label class="jump-link" @click="jumpToPreview(ship)">EXPLORE</label></td>
          </tr>
          </tbody>
        </table>
      </div>
      <div v-if="this.displayType==2">
        <table>
          <thead>
          <th>Name</th>
          <th>Location</th>
          <th>Price</th>
          </thead>
          <tbody>
          <tr v-for="(fishingClass,index) in this.filteredFishingClasses" @Click="selectEntity(fishingClass)"
              v-bind:index="index" :key="fishingClass.id" v-bind="{selected: selectedEntity.id===fishingClass.id}">
            <td>{{ fishingClass.name }}</td>
            <td>{{ fishingClass.address }}</td>
            <td>{{ fishingClass.priceList }}</td>
            <td><label class="jump-link" @click="jumpToPreview(fishingClass)">EXPLORE</label></td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios"

export default {

  name: "HomePage",
  data() {
    return {
      publicPath: process.env.BASE_URL,
      fishingClasses: [],
      vacationHouses: [],
      ships: [],
      selectedEntity: {},
      selectedRows: [],
      filter: "",
      selected: false,
      displayType: 0,  //represents type of renting entity, 0 vac houses, 1 ships , 2 fishing classes
    }
  }
  ,
  mounted() {
    axios.get("api/vacation_houses/all").then(response => this.vacationHouses = response.data);
    axios.get("api/fishingClasses/all").then(response => this.fishingClasses = response.data);
    axios.get("api/ships/all").then(response => this.ships = response.data);

  },
  computed: {
    filteredVacationHouses() {
      return this.vacationHouses.filter(row => {
        const name = row.name.toString().toLowerCase();
        const address = row.address.toString().toLowerCase();
        const price = row.priceList.toString().toLowerCase();
        const searchTerm = this.filter.toLowerCase();


        return name.includes(searchTerm) || address.includes(searchTerm) || price.includes(searchTerm);
      })
    },
    filteredShips() {
      return this.ships.filter(row => {
        const name = row.name.toString().toLowerCase();
        const address = row.address.toString().toLowerCase();
        const price = row.priceList.toString().toLowerCase();
        const searchTerm = this.filter.toLowerCase();


        return name.includes(searchTerm) || address.includes(searchTerm) || price.includes(searchTerm);
      })
    },
    filteredFishingClasses() {
      return this.fishingClasses.filter(row => {
        const name = row.name.toString().toLowerCase();
        const address = row.address.toString().toLowerCase();
        const price = row.priceList.toString().toLowerCase();
        const searchTerm = this.filter.toLowerCase();


        return name.includes(searchTerm) || address.includes(searchTerm) || price.includes(searchTerm);
      })
    },
  },
  methods: {
    goToUserProfile() {
      this.$router.push('/clientProfile');
    },
    goToFishingClassProfile() {
      this.$router.push('/fishingClassProfile');
    },
    goToVacationHousesProfile() {
      this.$router.push('/vacationHouses');
    },
    selectEntity(entity) {

      this.selectedEntity = entity;
      this.selected = true;

    },
    jumpToPreview(entity) {
      this.selectEntity(entity);
      this.$router.push({
        name: "rentingEntityPreview",
        params: {id: this.selectedEntity.id, displayType: this.displayType}
      });
    }

  }
}
</script>


<style scoped>

.main {
  margin: 10px;
}

.jump-link {
  color: blue;

}

.jump-link:hover {
  cursor: pointer;
  background-color: aquamarine;
}

button {
  margin: 20px;
}

.entities-div {
  text-align: center;
  margin: auto;
  width: 50%;
  padding: 10px;

}

.entities-div table {
  margin: auto;
}

.entities-div table td{
  width: 250px;
}

.radio {
  margin: 20px;
}

.inputSearch {
  width: 90%;
  height: 50px;
}


</style>