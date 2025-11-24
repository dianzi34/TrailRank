# TrailRank Setup Verification Script
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "TrailRank Setup Verification" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

# Check Java
Write-Host ""
Write-Host "Checking Java..." -ForegroundColor Yellow
try {
    $javaVersion = java -version 2>&1 | Select-Object -First 1
    Write-Host "Check Java installed: $javaVersion" -ForegroundColor Green
} catch {
    Write-Host "X Java not found" -ForegroundColor Red
}

# Check Maven
Write-Host ""
Write-Host "Checking Maven..." -ForegroundColor Yellow
try {
    $mavenVersion = mvn -version 2>&1 | Select-Object -First 1
    Write-Host "Check Maven installed: $mavenVersion" -ForegroundColor Green
} catch {
    Write-Host "X Maven not found" -ForegroundColor Red
}

# Check MySQL
Write-Host ""
Write-Host "Checking MySQL..." -ForegroundColor Yellow
try {
    $mysqlVersion = mysql --version 2>&1
    Write-Host "Check MySQL installed: $mysqlVersion" -ForegroundColor Green
} catch {
    Write-Host "X MySQL not found" -ForegroundColor Red
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "If all checks passed, you are ready!" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
