<template>
  <head>
      <title>User Profile</title>
  </head>
  <body >     
    <div class="grid">    
     
        <div class="top-left-cell">
          <button v-if="!this.editing" @click="toggleEdit()" class="btn btn-primary">Edit Info</button>        
          <button  class="btn btn-primary"  data-bs-toggle="modal" data-bs-target="#modalRequest">Delete My Account</button>  
        </div>

        <div v-if="!this.editing" class="center-left-cell">
          <img v-bind:src="'data:image/jpeg;base64,' + picture" style="width: 300px; height: 200px;">
          <br><br>
          <h1>{{user.firstName}} {{user.lastName}} </h1>        
          <p>E-mail: {{user.email}}</p>
          <p>Address: {{user.address}}</p>
          <p>City: {{user.city}}</p>
          <p>Country: {{user.country}}</p>
          <p>Phone Number: {{user.phoneNum}} </p>
          <p>Penalties: {{user.penalties}}</p>
        </div>
        <div v-else class="center-left-cell">
          <img v-bind:src="'data:image/jpeg;base64,' + picture" style="width: 300px; height: 200px;">     
          <br><br>
          <input type="file" @change="onFileSelected" accept="image/png, image/jpeg" >
          <button class="btn btn-secondary" @click="onUpload">Upload</button>
          <br><br>
          <div class="input-group mb-3">
            <span class="input-group-text" id="firstName">First Name:</span>
            <input v-model="user.firstName" type="text" class="form-control" aria-label="Username" aria-describedby="firstName">
          </div>
          <div class="input-group mb-3">
            <span class="input-group-text" id="lastName">Last Name:</span>
            <input v-model="user.lastName" type="text" class="form-control" aria-label="Username" aria-describedby="lastName">
          </div>
          <div class="input-group mb-3">
            <span class="input-group-text" id="address">Address: </span>
            <input v-model="user.address" type="text" class="form-control" aria-label="Username" aria-describedby="address">
          </div>
          <div class="input-group mb-3">
            <span class="input-group-text" id="city">City:</span>
            <input v-model="user.city" type="text" class="form-control" aria-label="Username" aria-describedby="city">
          </div>
          <div>
            <country-select class="form-select" v-model="user.country" :usei18n="false" :autocomplete="true" :countryName="true"/>
          </div>
          <br>
          <vue-tel-input v-model="phoneNum" @validate="telValidate"></vue-tel-input>
          <div>
            <button style="margin-right: 20px" class="btn btn-primary" @click="saveEdit()">Save</button>
            <button class="btn btn-primary" @click="cancelEdit()">Cancel</button>
          </div>
        </div>
        <div class="bot-left-cell">
         <button  class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalSubscriptions">My Subscriptions</button>
         <button  class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalDelete">Change Password</button>
        </div>
   


      <div class="modal fade" id="modalRequest" tabindex="-1" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-body">
                  <textarea v-model="text" rows="4" cols="50" placeholder="Reason..."/>
                  <button style="margin-right: 20px" class="btn btn-primary" @click="sendDeleteRequest()">Submit</button>
              </div>
              <div class="modal-footer">
                <button type="button" class="custom-btn button-primary" data-bs-dismiss="modal">Close</button>
              </div>
            </div>
          </div>
        </div>

      <div class="modal fade" id="modalSubscriptions" tabindex="-1" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-body">
                  <div class="subscriptions">
                    <div v-for="(subscription, index) in this.subscriptions" 
                        v-bind:index="index" :key="subscription.id" class="reservation">    
                        <div class="reservation-picture"><img v-bind:src="'data:image/jpeg;base64,' + subscription.img"></div>
                        <div style="display: flex;">        
                            <div class="reservation-left">                
                            {{subscription.name}}
                            </div>
                            <div clas="reservation-right">
                              <button @click="unsubscribe(subscription,index)">Unsubscribe</button>
                            </div>
                        </div>
                    </div>
                  </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="custom-btn button-primary" data-bs-dismiss="modal">Close</button>
              </div>
            </div>
          </div>
        </div>

        <div class="modal fade" id="modalDelete" tabindex="-1" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-body">
                <input v-model="oldPassword" type="password" placeholder="Old password">
                <input v-model="newPassword" type="password" placeholder="New password">
                <input v-model="confirmPassword"  type="password" placeholder="Confirm new password">
                <button style="margin-right: 20px" class="btn btn-primary" @click="changePassword()">Change</button>
              </div>
              <div class="modal-footer">
                <button type="button" class="custom-btn button-primary" data-bs-dismiss="modal">Close</button>
              </div>
            </div>
          </div>
        </div>

    </div>


    <div class="top-right-cell">
      <h3 class="main-heading">Active Reservations</h3>
      <div style="display: flex; align-content: space-between; margin: 15px 0 15px 0">
        <div style="width: 70%">
          <input class="input-group-text" type="text" placeholder="Filter by name, Renting Entity, Price..." v-model="filterR"/>
        </div>
        <br>
        <div style="width: 30%">
          <div style="width: 95%; float: right">
            <select class="form-select" v-model="sortTypeR" name="example">
              <option value=0>Place descending</option>
              <option value=1>Place ascending</option>
              <option value=2>Price descending</option>
              <option value=3>Price ascending</option>
            </select>
          </div>
        </div>
      </div>
    <div class="reservation" v-for="(reservation, index) in this.filteredReservations" v-bind:index="index" :key="reservation.id">
      <div class="reservation-picture"><img v-bind:src="'data:image/jpeg;base64,' + reservation.img"></div>
        <div style="display: flex;">
          <div class="reservation-left">
            <div><h5 style="font-weight: bold;">{{reservation.rentingEntityName}}</h5></div>
            <div><b>Address: </b> {{reservation.place}}</div>
            <div><b>Price: </b> {{reservation.price}}</div>
            <div><b>Starting Date: </b> {{reservation.start}}</div>
            <div><b>Duration: </b> {{msToTime(reservation.duration)}}</div>
            <div v-if="reservation.additionalServices.length != 0"> <b>Additional Services: </b>
              <span v-for="service in reservation.additionalServices.slice(0,-1)" :key="service"> {{service}}, </span>
              <span>{{reservation.additionalServices.at(-1)}}</span>
            </div>
          </div>
          <div class="reservation-right">
              <button class="custom-btn button-primary" @click="cancelReservation(reservation, index)">Cancel Reservation</button>
          </div>
        </div>
      </div>
    </div>
    
    <div class="bot-right-cell">
      <h3 class="main-heading">Reservation History</h3>
      <div style="display: flex; align-content: space-between; margin: 15px 0 15px 0">
        <div style="width: 70%">
          <input class="input-group-text" type="text" placeholder="Filter by name, Renting Entity, Price..." v-model="filterH"/>
        </div>
        <br>
        <div style="width: 30%">
          <div style="width: 95%; float: right">
            <select class="form-select" v-model="sortTypeH" name="example">
              <option value=0>Place descending</option>
              <option value=1>Place ascending</option>
              <option value=2>Price descending</option>
              <option value=3>Price ascending</option>
            </select>
          </div>
        </div>
      </div>
      <div class="reservation" v-for="(reservationHistory, index) in this.filteredReservationsHistory" v-bind:index="index" :key="reservationHistory.id">
        <div class="reservation-picture"><img v-bind:src="'data:image/jpeg;base64,' + reservationHistory.img"></div>
        <div style="display: flex;">
          <div class="reservation-left">
            <div><h5 style="font-weight: bold;">{{reservationHistory.rentingEntityName}}</h5></div>
            <div><b>Address: </b> {{reservationHistory.place}}</div>
            <div><b>Price: </b> {{reservationHistory.price}}</div>
            <div><b>Starting Date: </b> {{reservationHistory.start}}</div>
            <div><b>Duration: </b> {{msToTime(reservationHistory.duration)}}</div>
            <div v-if="reservationHistory.additionalServices.length != 0"> <b>Additional Services: </b>
              <span v-for="service in reservationHistory.additionalServices.slice(0,-1)" :key="service"> {{service}}, </span>
              <span>{{reservationHistory.additionalServices.at(-1)}}</span>
            </div>
          </div>
          <div class="reservation-right">
            <button style="margin-right: 10px" class="custom-btn button-primary" data-bs-toggle="modal" data-bs-target="#modalRate">Rate</button>
            <button class="custom-btn button-primary" data-bs-toggle="modal" data-bs-target="#modalReport">Report</button>
          </div>
        </div>

        <div class="modal fade" id="modalRate" tabindex="-1" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-body">
                <div>
                  <vue3-star-ratings :showControl=false v-model="ratingRE"/>
                  <textarea v-model="commentRatingRE" style="height: 100px" type="text" class="form-control" placeholder="Comment..."></textarea><br>
                  <button class="custom-btn button-primary" @click="rateRE(reservationHistory)">Rate renting entity</button>
                </div>
                <div>
                  <vue3-star-ratings :showControl=false v-model="ratingRO"/>
                  <textarea v-model="commentRatingRO" style="height: 100px" type="text" class="form-control" placeholder="Comment..."></textarea><br>

                  <button class="custom-btn button-primary" @click="rateRO(reservationHistory)">Rate owner</button>
                </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="custom-btn button-primary" data-bs-dismiss="modal">Close</button>
              </div>
            </div>
          </div>
        </div>

        <div class="modal fade" id="modalReport" tabindex="-1" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-body">
                <div>
                  <textarea v-model="commentReportRE" style="height: 100px" type="text" class="form-control" placeholder="Comment..."></textarea><br>
                  <button class="custom-btn button-primary" @click="reportRE(reservationHistory)">Submit Report</button>
                </div>
                <div>
                  <br><textarea v-model="commentReportRO" style="height: 100px" type="text" class="form-control" placeholder="Comment..."></textarea><br>
                  <button class="custom-btn button-primary" @click="reportRO(reservationHistory)">Submit Report</button><br>
                </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="custom-btn button-primary" data-bs-dismiss="modal">Close</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>


  </body>
</template>

<script>
import axios from "axios"
export default {
  name: "ClientProfile",
  data() {
    return {
      user:{},
      backUp:{},      
      selectedFile: null,
      picture: null,
      editing: false,
      validNumber: '',
      phoneNum: null,
      oldPassword:"",
      newPassword:"",
      confirmPassword:"",
      subscriptions:[],
      text: "",
      reservationsHistory:[],
      selectedReservationHistory:{},
      reservations:[],
      filterR:"",
      filterH: "",
      ratingRE:5,
      commentRatingRE:"",
      commentRatingRO:"",
      ratingRO:5,
      sortTypeR:0,
      sortTypeH: 0,
      commentReportRE:"",
      commentReportRO:"",

            }
    },
     mounted() {
      axios
        .get("/api/clients/loggedClient",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} } )
        .then(response => (this.user = response.data)).catch((errorP) => {
        
        if(errorP.response.status===401){
          //this.$toast.error("Not Logged In");
          this.$root.accessToken=null;
          localStorage.setItem("jwt",null);
          
          this.$router.push("/loginPage");
        }
       
      });
      axios.get("/api/users/loggedUser/picture",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => (this.picture=response.data)).catch((errorP) => {
        
        if(errorP.response.status===401){
          //this.$toast.error("Not Logged In");
          this.$root.accessToken=null;
          localStorage.setItem("jwt",null);
          
          this.$router.push("/loginPage");
        }
       
      });  
      axios.get("/api/clients/subscriptions",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => (this.subscriptions=response.data)).catch((errorP) => {
        
        if(errorP.response.status===401){
          //this.$toast.error("Not Logged In");
          this.$root.accessToken=null;
          localStorage.setItem("jwt",null);          
          this.$router.push("/loginPage");
        }
       
      }); 
        axios.get("/api/clients/loggedClient/reservations",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => (this.reservations=response.data)).catch((errorP) => {
        
        if(errorP.response.status===401){
          //this.$toast.error("Not Logged In");
          this.$root.accessToken=null;
          localStorage.setItem("jwt",null);          
          this.$router.push("/loginPage");
        }
       
      });
        axios.get("/api/clients/loggedClient/reservationHistory",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => (this.reservationsHistory=response.data)).catch((errorP) => {
        
        if(errorP.response.status===401){
          //this.$toast.error("Not Logged In");
          this.$root.accessToken=null;
          localStorage.setItem("jwt",null);          
          this.$router.push("/loginPage");
        }
       
      }); 
   
    },
    computed: {
    filteredReservationsHistory() {
      return this.reservationsHistory.filter(row => {
        return this.includesH(row);
      }).sort((a,b)=>{
        return this.sort(a,b,this.sortTypeH);})
    },
    filteredReservations(){
       return this.reservations.filter(row => {
        return this.includesH(row);
      }).sort((a,b)=>{
        return this.sort(a,b,this.sortTypeR);})
    }},
    methods: {    
    toggleEdit(){
          this.backUp={"firstName":this.user.firstName,"lastName":this.user.lastName,"address":this.user.address,"city":this.user.city,"country":this.user.country,"phoneNum":this.user.phoneNum};
    
          this.editing=true; 
      },

    changePassword(){
      var passwordRegex=/^[0-9A-Za-z]+$/;

      if(this.newPassword.length<5 || this.newPassword.length > 20){
        this.$toast.error("Password must be between 5-20 characters");
        return;
      }
      if(!passwordRegex.test(this.newPassword)){
        this.$toast.error("Password must only contain letters and numbers");
        return;
      }
      if(!passwordRegex.test(this.oldPassword)){
        this.$toast.error("Password must only contain letters and numbers");
        return;
      }
      if(this.newPassword !== this.confirmPassword){
        this.$toast.error("Passwords must match");
        return;
      }

      const passwordChange={"oldPassword":this.oldPassword,"newPassword":this.newPassword,"newPasswordConfirm":this.confirmPassword};
      console.log(passwordChange);
      axios.put(`/api/clients/changePassword`,passwordChange,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response =>{
        if(response.data==true){
          this.$root.accessToken=null;
          this.$root.signedIn=false;
          localStorage.setItem("jwt",null);          
          window.location.href = 'http://localhost:3000/loginPage';
        }else{
          this.$toast.error("Error");
        }
      });

    },

    sendDeleteRequest(){
      if(this.text.length<5){
        this.$toast.error("Provide a reason");
        return;
      }
      const request={"id":0,'submitterId':this.user.id,'submitterUsername':this.user.username,'text':this.text,'type':0};
      console.log(request);
      axios.post("/api/users/submitRequest",request,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => {if(response.data==true){this.$toast.success("Request Sent");}else{
          this.$toast.error("Error");
      }})
    },

    unsubscribe(subscription,index){
      axios.put(`/api/clients/unSubscribeDTO`,subscription,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response =>{
        if(response.data==true){
          this.$toast.success("Unsubscribed");
          this.subscriptions.splice(index,1);
        }else{
          this.$toast.error("Error");
        }
      });
    },

    saveEdit(){
      var regExp = /^[A-Za-z]+$/;
      
      if (this.user.firstName.length < 2 || this.user.firstName.length > 20) {
        this.$toast.error("First name must be between 2-20 characters");
        return;
        }
      if(regExp.test(this.user.firstName)==false){
        this.$toast.error("First name must contain only letters");
        return;
      }
      this.user.firstName=this.user.firstName.charAt(0).toUpperCase() + this.user.firstName.slice(1);
      
      if(this.user.lastName.length < 2 || this.user.lastName.length > 20){
        this.$toast.error("Last name must be between 2-20 characters");
        return;
      }
      if(regExp.test(this.user.lastName)==false){
        this.$toast.error("Last name must contain only letters");
        return;
      }
      this.user.lastName=this.user.lastName.charAt(0).toUpperCase() + this.user.lastName.slice(1);
      if(this.user.address === ''){
        this.$toast.error("Provide address");
        return;
      }
      if(this.user.city === ''){
        this.$toast.error("Provide city name");
        return;
      } 
      
      if(this.country == ""){
        this.$toast.error("Provide country");
        return;
      }
      if(this.validNumber===''){
        this.$toast.error("Phone number invalid");
        return;
      }
          
      this.user.phoneNum=this.validNumber;
      
      axios.post("/api/clients/loggedClient",this.user,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => this.user=response.data ).then(this.toastMessage());
      this.editing=false;
    },
    telValidate(phoneNum) {
      if (phoneNum.valid) {
        this.validNumber = phoneNum.number;
      } else {
        this.validNumber = '';
      } 
    },
    cancelEdit(){
      this.user.firstName=this.backUp.firstName;
      this.user.lastName=this.backUp.lastName;
      this.user.address=this.backUp.address;
      this.user.city=this.backUp.city;
      this.user.country=this.backUp.country;
      this.user.phoneNum=this.backUp.phoneNum;
      this.editing=false;
    },
    toastMessage(){
      this.$toast.success("Info updated");
    },
    onFileSelected(event){
      this.selectedFile= event.target.files[0];
    },
    onUpload(){
      if(this.selectedFile == null){
          this.$toast.info("Select a file first ...");
      }else{
      if(this.selectedFile.size > 1048576){
        this.$toast.info("File is too large ...\nMust be smaller than 1Mb");
      }else{
      const fd=new FormData();
      fd.append('image',this.selectedFile,this.selectedFile.name);
      axios.post("/api/clients/loggedClient/picture",fd,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} },{
        onUploadProgress: uploadEvent => {
          console.log("Upload Progress: "+ Math.round(uploadEvent.loaded/uploadEvent.total * 100) + '%');
        }
      }).then(response => (this.picture=response.data))
    }}
    },
    dateFromLocal(dateString){
      if(dateString==null){
        return new Date();
      }
      
      const parts=dateString.split(" ");
      
      if( parts.length !=2){
        return new Date();
      }
      const dateParts= parts[0].split(".");
      
      if( dateParts.length !=3){
        return new Date();
      }
      const hourParts=parts[1].split(":");
      
      if( hourParts.length !=2){
        return new Date();
      }
      
      const day = dateParts[0];
      const month = dateParts[1];
      const year = dateParts[2];
      const hours = hourParts[0];
      const minutes = hourParts[1];
      // new Date(year, month, day, hours, minutes, seconds, milliseconds)
      const date= new Date(year,month-1,day,hours,minutes);
      return date;
    }, 
    cancelReservation(reservation, index){
      
      const date=this.dateFromLocal(reservation.start);
      let dateCheck=new Date();
      
      dateCheck.setDate(dateCheck.getDate() + 3);

      if(date < dateCheck){
        this.$toast.error("You can not cancel the reservation within the 3 days");
      }else{
        axios.put("/api/clients/cancelReservation",reservation,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => {
          if(response.data == true){
            this.$toast.success("You have successfully canceled the reservation");
            this.reservations.splice(index,1);
          }else{
            this.$toast.error("You can not cancel the reservation within the 3 days");
          }
        })
        
      }
    },

    msToTime(seconds) {     
      seconds = Number(seconds)/1000;
      var d = Math.floor(seconds / (3600*24));
      var h = Math.floor(seconds % (3600*24) / 3600);

      var dDisplay = d > 0 ? d + (d == 1 ? " day " : " days ") : "";
      if (h != 0) {
        dDisplay = d > 0 ? d + (d == 1 ? " day, " : " days, ") : "";
        var hDisplay = h > 0 ? h + (h == 1 ? " hour " : " hours ") : "";
        return dDisplay + hDisplay;
      }

      return dDisplay
  },
  rateRE(reservationHistory){
    const review={"id":0,'rentingEntityId':reservationHistory.rentingEntityId,'rentingOwnerId':reservationHistory.rentingEntityOwnerId,'clientId':0,'rating':this.ratingRE,'comment':this.commentRatingRE};
    axios.post("/api/clients/addReviewRE",review,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => {if(response.data==true){this.$toast.success("Review Added");}else{
      this.$toast.error("Error");
    }})
  },
  rateRO(reservationHistory){
    const review={"id":0,'rentingEntityId':reservationHistory.rentingEntityId,'rentingOwnerId':reservationHistory.rentingEntityOwnerId,'clientId':0,'rating':this.ratingRO,'comment':this.commentRatingRO};
    axios.post("/api/clients/addReviewRO",review,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => {if(response.data==true){this.$toast.success("Review Added");}else{
      this.$toast.error("Error");
    }})

  },
  reportRE(reservationHistory){
    if(this.commentReportRE.length<5){
        this.$toast.error("Provide a comment");
        return;
      }
 
    const complaint={"id":0,"complainantId":0,"rentingEntityId":reservationHistory.rentingEntityId,"respodentId":0,"text":this.commentReportRE};
    axios.post("/api/clients/addComplaintRE",complaint,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => {if(response.data==true){this.$toast.success("Complaint Sent");}else{
          this.$toast.error("Error");
        }})
  },
  reportRO(reservationHistory){
    if(this.commentReportRO.length<5){
        this.$toast.error("Provide a comment");
        return;
      }
    const complaint={"id":0,"complainantId":0,"rentingEntityId":0,"respodentId":reservationHistory.rentingEntityOwnerId,"text":this.commentReportRO};
    axios.post("/api/clients/addComplaintRO",complaint,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => {if(response.data==true){this.$toast.success("Complaint Sent");}else{
          this.$toast.error("Error");
        }})
  },
  includesH(row){
        
        const name = row.place.toString().toLowerCase();
        const rentingEntityName = row.rentingEntityName.toString().toLowerCase();
        const price = row.price.toString().toLowerCase();
        const searchTerm = this.filterH.toLowerCase();
        return (name.includes(searchTerm) || rentingEntityName.includes(searchTerm) || price.includes(searchTerm))
        
    },
    sort(a,b,sortType){
        if(sortType==0){
          let fa = a.place.toLowerCase(), fb = b.place.toLowerCase();
          if (fa < fb) {
            return -1
          }
          if (fa > fb) {
            return 1
          }
          return 0
        }
        else if (sortType== 1){
          let fa = a.place.toLowerCase(), fb = b.place.toLowerCase();
           if (fa > fb) {
            return -1
          }
          if (fa < fb) {
            return 1
          }
          return 0
        }
        if(sortType==2){
        let fa =parseInt(a.price), fb =parseInt(b.price)
        
          if (fa > fb) {
            return -1
          }
          if (fa < fb) {
            return 1
          }
          return 0
        }
        else if (sortType == 3){
          
          let fa = parseInt(a.price), fb =parseInt(b.price);
          
           if (fa < fb) {
            return -1
          }
          if (fa > fb) {
            return 1
          }
          return 0
        }

    },
  }

  
}
</script>

<style scoped>

.grid{
  display: grid; 
  grid-template-columns: 1fr 2fr; 
  grid-template-rows: 1fr 1fr 1fr 1fr; 
  gap: 10px 10px; 
  justify-content: center; 
  align-content: center; 
  justify-items: center; 
  align-items: center; 
}

.top-left-cell{
  grid-column: 1;
  grid-row: 1;
}

.center-left-cell{
  grid-column: 1;
  grid-row: 2 / 3;
}

.bot-left-cell{
  grid-column: 1;
  grid-row: 3;
}

.bot-right-cell{
  grid-column: 2;
  grid-row: 1;
}

.top-right-cell{
  grid-column-start: 2;
  grid-column-end: 3;
  grid-row-start: 3;
  grid-row-end: 5;
}

.reservation {
  text-align: center;
  width: 100%;
  height: 170px;
  border: 1px solid darkgrey;
  border-radius: 5px;
  overflow: hidden;
  margin-bottom: 10px;
}

.reservation-picture img {
  object-fit: cover;
  margin: 10px;
  border-radius: 5px;
  width: 200px;
  height: 150px;
}

.reservation-picture {
  float:left;
}

.reservation-left {
  float: left;
  margin: 10px;
  text-align: left;
  width: 60%;
}

.reservation-right {
  float: right;
  margin: auto;
  text-align: center;
  width: 30%;
  overflow: hidden;
}

</style>