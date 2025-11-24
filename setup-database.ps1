# TrailRank Database Setup Script
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "TrailRank Database Setup" -ForegroundColor Cyan
Write-Host "========================================`n" -ForegroundColor Cyan

# Prompt for MySQL password
Write-Host "Enter your MySQL root password:" -ForegroundColor Yellow
$mysqlPassword = Read-Host -AsSecureString
$plainPassword = [System.Runtime.InteropServices.Marshal]::PtrToStringAuto([System.Runtime.InteropServices.Marshal]::SecureStringToBSTR($mysqlPassword))

Write-Host "`nStep 1: Creating database and tables..." -ForegroundColor Yellow

# Create database and import schema
$schemaPath = "src\main\resources\trailrank_schema.sql"
if (Test-Path $schemaPath) {
    try {
        Get-Content $schemaPath | mysql -u root -p"$plainPassword" 2>&1
        Write-Host "✓ Database created successfully!" -ForegroundColor Green
    } catch {
        Write-Host "✗ Error creating database. Please check your password and try again." -ForegroundColor Red
        exit 1
    }
} else {
    Write-Host "✗ Schema file not found at $schemaPath" -ForegroundColor Red
    exit 1
}

Write-Host "`nStep 2: Updating application.properties..." -ForegroundColor Yellow

# Update application.properties with the password
$propsPath = "src\main\resources\application.properties"
if (Test-Path $propsPath) {
    $content = Get-Content $propsPath -Raw
    $content = $content -replace 'spring\.datasource\.password=.*', "spring.datasource.password=$plainPassword"
    Set-Content -Path $propsPath -Value $content
    Write-Host "✓ Configuration updated!" -ForegroundColor Green
} else {
    Write-Host "✗ application.properties not found" -ForegroundColor Red
    exit 1
}

Write-Host "`n========================================" -ForegroundColor Cyan
Write-Host "Database setup complete!" -ForegroundColor Green
Write-Host "You can now run the application with:" -ForegroundColor Cyan
Write-Host "  .\mvnw.cmd spring-boot:run" -ForegroundColor White
Write-Host "========================================" -ForegroundColor Cyan

