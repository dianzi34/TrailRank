# TrailRank Setup Verification Script
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "TrailRank Setup Verification" -ForegroundColor Cyan
Write-Host "========================================`n" -ForegroundColor Cyan

# Check Java
Write-Host "Checking Java..." -ForegroundColor Yellow
try {
    $javaVersion = java -version 2>&1 | Select-Object -First 1
    Write-Host "✓ Java installed: $javaVersion" -ForegroundColor Green
} catch {
    Write-Host "✗ Java not found" -ForegroundColor Red
}

# Check Maven
Write-Host "`nChecking Maven..." -ForegroundColor Yellow
try {
    $mavenVersion = mvn -version 2>&1 | Select-Object -First 1
    Write-Host "✓ Maven installed: $mavenVersion" -ForegroundColor Green
} catch {
    Write-Host "✗ Maven not found" -ForegroundColor Red
}

# Check MySQL
Write-Host "`nChecking MySQL..." -ForegroundColor Yellow
try {
    $mysqlVersion = mysql --version 2>&1
    Write-Host "✓ MySQL installed: $mysqlVersion" -ForegroundColor Green
} catch {
    Write-Host "✗ MySQL not found" -ForegroundColor Red
}

Write-Host "`n========================================" -ForegroundColor Cyan
Write-Host "If all checks passed, you're ready to continue!" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

