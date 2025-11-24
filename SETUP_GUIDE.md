# 🚀 TrailRank Setup Guide for Windows

Follow these steps to get TrailRank running on your computer!

---

## 📋 Quick Setup Checklist

- [ ] Install Chocolatey (Package Manager)
- [ ] Install Java 17
- [ ] Install Maven
- [ ] Install MySQL
- [ ] Set up the database
- [ ] Configure database password
- [ ] Run the application

---

## Step 1️⃣: Install Chocolatey

Chocolatey is a package manager for Windows that makes installing software easy.

### Instructions:
1. **Open PowerShell as Administrator**:
   - Press `Win + X` on your keyboard
   - Click "Windows PowerShell (Admin)" or "Terminal (Admin)"
   - Click "Yes" if prompted by User Account Control

2. **Copy and paste this command**:
```powershell
Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
```

3. **Wait for installation** (takes about 1-2 minutes)

4. **Close and reopen PowerShell as Administrator** (important!)

---

## Step 2️⃣: Install Required Software

In your **Administrator PowerShell**, run these commands **one at a time**:

### Install Java 17:
```powershell
choco install openjdk17 -y
```
Wait for it to complete...

### Install Maven:
```powershell
choco install maven -y
```
Wait for it to complete...

### Install MySQL:
```powershell
choco install mysql -y
```

**⚠️ IMPORTANT**: During MySQL installation, you'll be asked to set a **root password**.
- Choose something simple like `root123` or `password`
- **Remember this password!** You'll need it in the next steps

---

## Step 3️⃣: Verify Installation

1. **Close PowerShell** and **open a NEW regular PowerShell** (not as admin)

2. Navigate to your TrailRank folder:
```powershell
cd C:\Users\hailey\Desktop\TrailRank
```

3. Run the verification script:
```powershell
.\verify-setup.ps1
```

You should see ✓ green checkmarks for Java, Maven, and MySQL.

---

## Step 4️⃣: Set Up Database

Still in the TrailRank folder, run:

```powershell
.\setup-database.ps1
```

This script will:
1. Ask for your MySQL root password (enter the password you set during MySQL installation)
2. Create the `TrailRank` database
3. Create all necessary tables
4. Update your configuration file automatically

---

## Step 5️⃣: Run the Application

Now you're ready to run TrailRank! Run:

```powershell
.\mvnw.cmd spring-boot:run
```

**Wait for the application to start** (takes about 20-30 seconds on first run)

When you see:
```
Started TrailRankApplication in X.XXX seconds
```

**Open your browser** and go to: **http://localhost:8082**

🎉 **You should see the TrailRank welcome page!**

---

## 🛑 Troubleshooting

### Problem: "Java not found" after installation
**Solution**: Close all PowerShell windows and open a new one. The PATH needs to refresh.

### Problem: MySQL connection error
**Solution**: 
1. Make sure MySQL service is running:
```powershell
Get-Service MySQL*
```
2. If it's not running, start it:
```powershell
Start-Service MySQL80
```

### Problem: "mvnw.cmd not found"
**Solution**: Make sure you're in the TrailRank directory:
```powershell
cd C:\Users\hailey\Desktop\TrailRank
```

### Problem: Port 8082 already in use
**Solution**: Change the port in `src\main\resources\application.properties`:
```properties
server.port=8083
```
Then access the app at `http://localhost:8083`

---

## 📝 Quick Reference Commands

### Start the application:
```powershell
.\mvnw.cmd spring-boot:run
```

### Stop the application:
Press `Ctrl + C` in the PowerShell window

### Access the application:
Open browser to: http://localhost:8082

### Check MySQL service:
```powershell
Get-Service MySQL*
```

---

## 🎯 Next Steps

Once the application is running, you can:
1. Register a new user account
2. Browse hiking trails
3. Rate and review trails
4. Create collections (wish-to-hike, completed)
5. Track your progress

---

## 📞 Need More Help?

If you run into any issues:
1. Check the error messages in PowerShell
2. Verify all software is installed: `.\verify-setup.ps1`
3. Make sure MySQL is running
4. Check that your database password is correct in `application.properties`

Good luck! 🥾 Happy hiking with TrailRank!

