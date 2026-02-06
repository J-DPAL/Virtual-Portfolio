# Test SMTP connection to Gmail
$smtpServer = $env:MAIL_HOST
$smtpPort = $env:MAIL_PORT
$username = $env:MAIL_USERNAME
$password = $env:MAIL_PASSWORD

if (-not $smtpServer -or -not $smtpPort -or -not $username -or -not $password) {
    Write-Host "Missing SMTP environment variables. Set MAIL_HOST, MAIL_PORT, MAIL_USERNAME, and MAIL_PASSWORD." -ForegroundColor Yellow
    exit 1
}

try {
    $securePassword = ConvertTo-SecureString $password -AsPlainText -Force
    $credential = New-Object System.Management.Automation.PSCredential($username, $securePassword)
    
    $mailParams = @{
        SmtpServer = $smtpServer
        Port = $smtpPort
        UseSsl = $true
        Credential = $credential
        From = $username
        To = $username
        Subject = "SMTP Test"
        Body = "Testing SMTP connection"
    }
    
    Send-MailMessage @mailParams
    Write-Host "SUCCESS: Email sent successfully!" -ForegroundColor Green
    Write-Host "The app password is valid and SMTP is working." -ForegroundColor Green
}
catch {
    Write-Host "FAILED: Could not send email" -ForegroundColor Red
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "`nPossible issues:" -ForegroundColor Yellow
    Write-Host "1. App password is incorrect - regenerate it in Google Account settings" -ForegroundColor Yellow
    Write-Host "2. 2-Step Verification is not enabled on your Gmail account" -ForegroundColor Yellow
    Write-Host "3. Your account doesn't allow app passwords (Workspace accounts may need admin approval)" -ForegroundColor Yellow
}
