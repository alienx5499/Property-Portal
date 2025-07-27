# Security Policy

## 🛡️ Reporting a Vulnerability

At Property Portal, we take security seriously. We appreciate your efforts to responsibly disclose your findings and will make every effort to acknowledge your contributions.

### 📝 Reporting Process

1. **DO NOT** create public GitHub issues for security vulnerabilities
2. Go to the [Security tab](https://github.com/alienx5499/property-portal/security) of the repository
3. Click on "Report a vulnerability" to start a private discussion
4. Include the following information in your report:
   - Description of the vulnerability
   - Steps to reproduce the issue
   - Proof of concept if possible
   - Impact of the issue
   - Suggested fix (if any)

For more information on reporting security vulnerabilities, see [GitHub's documentation on privately reporting a security vulnerability](https://docs.github.com/en/code-security/security-advisories/guidance-on-reporting-and-writing/privately-reporting-a-security-vulnerability).

### ⏱️ Response Timeline

We aim to respond to security reports with the following timeline:

- **Initial Response**: Within 48 hours
- **Status Update**: Within 5 business days
- **Vulnerability Fix**: Timeline will vary based on severity and complexity

## 🔒 Security Measures

### Code Security
- All code changes undergo security review during PR process
- Regular dependency updates via Dependabot
- Automated security scanning for known vulnerabilities
- Code signing for all releases

### Data Security
- Database credentials are stored locally in database.properties (not in repository)
- All database operations use parameterized queries to prevent SQL injection
- No sensitive data is logged or exposed in error messages
- Database connections are properly closed after use

### Database Security
- MySQL user permissions are properly configured
- Database schema uses appropriate data types and constraints
- Foreign key relationships ensure data integrity
- Indexes are optimized for performance and security

## 🔄 Version Support

| Version | Supported          |
| ------- | ------------------ |
| 1.x.x   | :white_check_mark: |
| < 1.0   | :x:                |

## 🛠️ Security Best Practices

### For Contributors
1. **Code Review**
   - Follow secure coding guidelines
   - Review for common vulnerabilities (XSS, CSRF, etc.)
   - Validate all user inputs
   - Use safe methods for data handling

2. **Dependencies**
   - Keep dependencies up to date
   - Review security advisories
   - Use only trusted packages
   - Regular audit of package.json

3. **Testing**
   - Include security tests
   - Perform penetration testing
   - Regular vulnerability scanning
   - Test edge cases thoroughly

### For Users
1. **Browser Security**
   - Use modern, updated browsers
   - Enable security features
   - Be cautious with browser extensions
   - Report suspicious behavior

2. **Data Safety**
   - Don't input sensitive data
   - Clear browser cache if needed
   - Use private browsing when preferred
   - Be aware of data handling practices

## 🔐 Security Features

### Current Implementation
- Input validation for all database inputs
- Parameterized queries to prevent SQL injection
- Proper error handling without exposing sensitive data
- Secure database connection management
- Transaction management for data integrity

### Planned Improvements
- Enhanced database connection pooling
- Additional input validation for all fields
- Improved error logging without sensitive data
- Extended database security auditing
- Advanced transaction management

## 📋 Vulnerability Disclosure Policy

### Scope
- Java application code
- Database schema and queries
- Model classes and DAO implementations
- Service layer business logic
- Database connection management

### Out of Scope
- Known issues marked as `wontfix`
- Theoretical vulnerabilities without proof of concept
- Issues requiring physical access to the database server
- Social engineering attacks
- DOS/DDOS attacks on the database server

## 🤝 Acknowledgments

We would like to thank all security researchers and contributors who help keep SortVision secure. Notable contributors will be listed here (with permission).

## 📜 License

Security policy and procedures are licensed under [MIT License](LICENSE).

## 📞 Contact

For security concerns, please use GitHub's private vulnerability reporting system:
1. Visit the [Security tab](https://github.com/alienx5499/property-portal/security)
2. Click "Report a vulnerability"
3. Follow the provided template

Security Team Lead: [@alienx5499](https://github.com/alienx5499)

---

*Last updated: 2025-07-27* 