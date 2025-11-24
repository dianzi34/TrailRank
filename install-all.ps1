# TrailRank - Install All Prerequisites
# Right-click this file and select "Run with PowerShell as Administrator"

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "TrailRank Prerequisites Installer" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Check if running as administrator
$currentPrincipal = New-Object Security.Principal.WindowsPrincipal([Security.Principal.WindowsIdentity]::GetCurrent())
$isAdmin = $currentPrincipal.IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)

if (-not $isAdmin) {
    Write-Host "ERROR: This script must be run as Administrator!" -ForegroundColor Red
    Write-Host ""
    Write-Host "Please:" -ForegroundColor Yellow
    Write-Host "1. Right-click this file (install-all.ps1)" -ForegroundColor Yellow
    Write-Host "2. Select 'Run with PowerShell as Administrator'" -ForegroundColor Yellow
    Write-Host ""
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host "Running with Administrator privileges - Good!" -ForegroundColor Green
Write-Host ""

# Check if Chocolatey is installed
Write-Host "Step 1: Checking for Chocolatey..." -ForegroundColor Yellow
try {
    $chocoVersion = choco --version 2>&1
    Write-Host "Chocolatey is already installed: $chocoVersion" -ForegroundColor Green
} catch {
    Write-Host "Chocolatey not found. Installing..." -ForegroundColor Yellow
    Set-ExecutionPolicy Bypass -Scope Process -Force
    [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072
    iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
    
    # Refresh environment
    $env:Path = [System.Environment]::GetEnvironmentVariable("Path","Machine") + ";" + [System.Environment]::GetEnvironmentVariable("Path","User")
    
    Write-Host "Chocolatey installed successfully!" -ForegroundColor Green
}

Write-Host ""
Write-Host "Step 2: Installing Java 17..." -ForegroundColor Yellow
choco install openjdk17 -y
Write-Host "Java installation complete!" -ForegroundColor Green

Write-Host ""
Write-Host "Step 3: Installing Maven..." -ForegroundColor Yellow
choco install maven -y
Write-Host "Maven installation complete!" -ForegroundColor Green

Write-Host ""
Write-Host "Step 4: Installing MySQL..." -ForegroundColor Yellow
Write-Host "IMPORTANT: You will be asked to set a root password." -ForegroundColor Red
Write-Host "Choose something simple like 'root123' and REMEMBER IT!" -ForegroundColor Red
Write-Host ""
Read-Host "Press Enter to continue with MySQL installation"
choco install mysql -y
Write-Host "MySQL installation complete!" -ForegroundColor Green

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Installation Complete!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Next steps:" -ForegroundColor Yellow
Write-Host "1. Close this window" -ForegroundColor White
Write-Host "2. Go back to your regular PowerShell/Terminal" -ForegroundColor White
Write-Host "3. Run: .\verify-setup.ps1" -ForegroundColor White
Write-Host ""
Read-Host "Press Enter to exit"

