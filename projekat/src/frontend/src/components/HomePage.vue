<template>
  <nav class="navbar">
      <img class="brand" :src="`${publicPath}app-logo.png`">
      <ul>
        <li>
          <a href="#">Sing In</a>
        </li>
        <li>
          <a href="#">Register</a>
        </li>        
      </ul>
    </nav>
  <div class="main">
    <button class="btn btn-primary" @click="goToUserProfile()">User Profile</button>
    <button class="btn btn-primary" @click="goToFishingClassProfile()">Fishing Class Profile</button>
    <button class="btn btn-primary" @click="goToVacationHousesProfile()">Vacation House Profile</button>
    <div class="entities-div" align='center'>
      <div>
          <input type="text" placeholder="Filter by name, address..." v-model="filter" />
      </div>
      <div >
          <input type="radio" id="contactChoice1"
              name="choice" value=0  v-model="displayType">
          <label for="contactChoice1">Vacation Houses</label>
          <input type="radio" id="contactChoice2"
                name="choice" value=1 v-model="displayType">
          <label for="contactChoice2">Ships</label>
          <input type="radio" id="contactChoice3"
                name="choice" value=2  v-model="displayType">
          <label for="contactChoice3">Fishing Classes</label>
      </div>
      <div v-if="this.displayType==0">
        <table >
            <thead>
              <th>Name</th>
              <th>Address</th>
              <th>Price</th>
            </thead>
            <tbody>
              <tr v-for="(vacationHouse,index) in this.filteredVacationHouses" @Click="selectEntity(vacationHouse)" v-bind:index="index" :key="vacationHouse.id"  v-bind="{selected: selectedEntity.id===vacationHouse.id}">
                <td>{{vacationHouse.name}}</td>
                <td>{{vacationHouse.address}}</td>
                <td>{{vacationHouse.priceList}}</td>
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
              <tr v-for="(ship,index) in this.filteredShips" @Click="selectEntity(ship)" v-bind:index="index" :key="ship.id"  v-bind="{selected: selectedEntity.id===ship.id}">
                <td>{{ship.name}}</td>
                <td>{{ship.address}}</td>
                <td>{{ship.priceList}}</td>
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
              <tr v-for="(fishingClass,index) in this.filteredFishingClasses"  @Click="selectEntity(fishingClass)" v-bind:index="index" :key="fishingClass.id" v-bind="{selected: selectedEntity.id===fishingClass.id}">
                <td>{{fishingClass.name}}</td>
                <td>{{fishingClass.address}}</td>
                <td>{{fishingClass.priceList}}</td>
              </tr>
            </tbody>
        </table>      
      </div>     
    </div>
    <br><br><br>
    <div v-if="this.selected" align='center'>
      <p>Name: {{selectedEntity.name}}</p>
      <p>Address: {{selectedEntity.address}}</p>
      <p>Promo Description: {{selectedEntity.promoDescription}}</p>
      <p>Behavior Rules: {{selectedEntity.behaviourRules}}</p>
      <p>Price: {{selectedEntity.priceList}}</p>
      <p>Additional Info: {{selectedEntity.additionalInfo}}</p>
      <p>Cancellation conditions: {{selectedEntity.cancellationConditions}}</p>
      <p>Pictures: //to do insert pictures//</p>
      <p v-if="this.displayType==0">Rooms Quantity: {{selectedEntity.roomsQuantity}}</p>
      <p v-if="this.displayType==0">Beds Per Room: {{selectedEntity.bedsPerRoom}}</p>
      <p v-if="this.displayType==1">Type: {{selectedEntity.type}}</p>
      <p v-if="this.displayType==1">Length: {{selectedEntity.length}}</p>
      <p v-if="this.displayType==1">Engine Number: {{selectedEntity.engineNumber}}</p>
      <p v-if="this.displayType==1">Engine Power: {{selectedEntity.enginePower}}</p>
      <p v-if="this.displayType==1">Top Speed: {{selectedEntity.topSpeed}}</p>
      <p v-if="this.displayType==1">Client Limit: {{selectedEntity.clientLimit}}</p>
      <p v-if="this.displayType==2">Instructor Biography: {{selectedEntity.instructorBiography}}</p>
      <p v-if="this.displayType==2">Client Limit: {{selectedEntity.clientLimit}}</p>
      <button @click="clearSelection()">Hide</button>
    </div>
  </div>
</template>

<script>
import axios from "axios"

export default {

  name: "HomePage",
  data(){
    return {
      publicPath: process.env.BASE_URL,
      fishingClasses:[],
      vacationHouses: [],
      ships: [],
      selectedEntity: {},
      selectedRows: [],
      filter: "",
      selected: false,
      displayType:0,  //represents type of renting entity, 0 vac houses, 1 ships , 2 fishing classes
    }
  }
  ,
  mounted(){
      axios.get("api/vacation_houses/all").then( response => this.vacationHouses=response.data);
      axios.get("api/fishingClasses/all").then( response => this.fishingClasses=response.data);
      axios.get("api/ships/all").then( response => this.ships=response.data);

  },
  computed:{
    filteredVacationHouses(){
      return this.vacationHouses.filter( row => {
        const name = row.name.toString().toLowerCase();
        const address=row.address.toString().toLowerCase();
        const price=row.priceList.toString().toLowerCase();
        const searchTerm = this.filter.toLowerCase();

        
        return name.includes(searchTerm) || address.includes(searchTerm) || price.includes(searchTerm);
      })
    },
    filteredShips(){
      return this.ships.filter( row => {
        const name = row.name.toString().toLowerCase();
        const address=row.address.toString().toLowerCase();
        const price=row.priceList.toString().toLowerCase();
        const searchTerm = this.filter.toLowerCase();

        
        return name.includes(searchTerm) || address.includes(searchTerm) || price.includes(searchTerm);
      })
    },
    filteredFishingClasses(){
      return this.fishingClasses.filter( row => {
        const name = row.name.toString().toLowerCase();
        const address=row.address.toString().toLowerCase();
        const price=row.priceList.toString().toLowerCase();
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
    selectEntity(entity){
      this.selectedEntity=entity;
      this.selected=true;      
    },
    clearSelection(){
      this.selectedEntity={};
      this.selected=false;
    }
    
  }
}
</script>


<style scoped>

.main {
  margin: 10px;
}

button {
  margin: 20px;
}

.entities-div{
  padding: 10px 10px 10px 10px;  
  
}

a {
  text-decoration: none;
}

.navbar {
  background-color: #333333;
  display: flex;
  /* let's align the items so everything is nice and centered */
  align-items: center;
}

/* We want all links in the navbar to be #e7e7e7 */
.navbar a {
  color: #e7e7e7;
}

.navbar ul {
 /* Let's reset the margin and padding of the list so it's is flush. */
  margin: 0;
  padding: 0;
  /* Let's remove the bullet points */
  list-style-type: none;
  /* display the list flex and align the items centered */
  display: flex;
  align-items:center;
}

.navbar .brand {
  /*Let's make the anchor display block.
     This will ensure when we get the full width of the container.
  */
  
 /* Remove padding from top/bottom and add 24px padding right/left */
  padding: 0 24px;
  min-width: 60px;
  max-height: 70px;
}

.navbar ul li a {
  color: #e7e7e7;
  padding: 24px;
  /*Let's make the anchor display block.
     This will ensure when we hover, focus, or is active we get the full width and height 
      of the container.
  */
  display: block;
}

/* When a user hovers, focuses or if the link is active change the background */
.navbar ul a:hover,
.navbar ul a:focus,
.navbar ul .active {
  background-color: #272727;
}

</style>