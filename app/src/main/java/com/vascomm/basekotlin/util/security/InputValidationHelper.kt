package com.vascomm.basekotlin.util.security

import android.util.Patterns
import java.util.regex.Pattern

/**
 * Input Validation Helper
 * Validates user input to prevent injection attacks and ensure data integrity
 */
object InputValidationHelper {

    /**
     * Validate email format
     */
    fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Validate phone number (Indonesia format)
     */
    fun isValidPhoneNumber(phone: String): Boolean {
        val phonePattern = "^(\\+62|62|0)[0-9]{9,12}$"
        return phone.matches(phonePattern.toRegex())
    }

    /**
     * Validate password strength
     * At least 8 characters, contains uppercase, lowercase, number, and special character
     */
    fun isValidPassword(password: String): Boolean {
        if (password.length < 8) return false

        val hasUpperCase = password.any { it.isUpperCase() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecialChar = password.any { !it.isLetterOrDigit() }

        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar
    }

    /**
     * Validate password with minimum length only
     */
    fun isValidPasswordMinLength(password: String, minLength: Int = 6): Boolean {
        return password.length >= minLength
    }

    /**
     * Validate username (alphanumeric and underscore only)
     */
    fun isValidUsername(username: String): Boolean {
        val usernamePattern = "^[a-zA-Z0-9_]{3,20}$"
        return username.matches(usernamePattern.toRegex())
    }

    /**
     * Validate numeric input
     */
    fun isValidNumeric(input: String): Boolean {
        return input.matches("^[0-9]+$".toRegex())
    }

    /**
     * Validate alphabetic input only
     */
    fun isValidAlphabetic(input: String): Boolean {
        return input.matches("^[a-zA-Z\\s]+$".toRegex())
    }

    /**
     * Validate alphanumeric input
     */
    fun isValidAlphanumeric(input: String): Boolean {
        return input.matches("^[a-zA-Z0-9\\s]+$".toRegex())
    }

    /**
     * Sanitize input to prevent SQL injection
     */
    fun sanitizeInput(input: String): String {
        return input
            .replace("'", "")
            .replace("\"", "")
            .replace(";", "")
            .replace("--", "")
            .replace("/*", "")
            .replace("*/", "")
            .replace("xp_", "")
            .replace("sp_", "")
            .replace("DROP", "", ignoreCase = true)
            .replace("DELETE", "", ignoreCase = true)
            .replace("INSERT", "", ignoreCase = true)
            .replace("UPDATE", "", ignoreCase = true)
            .replace("SELECT", "", ignoreCase = true)
            .trim()
    }

    /**
     * Sanitize HTML input to prevent XSS
     */
    fun sanitizeHtml(input: String): String {
        return input
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("&", "&amp;")
            .replace("\"", "&quot;")
            .replace("'", "&#x27;")
            .replace("/", "&#x2F;")
    }

    /**
     * Validate input length
     */
    fun isValidLength(input: String, minLength: Int, maxLength: Int): Boolean {
        return input.length in minLength..maxLength
    }

    /**
     * Validate URL format
     */
    fun isValidUrl(url: String): Boolean {
        return Patterns.WEB_URL.matcher(url).matches()
    }

    /**
     * Validate date format (yyyy-MM-dd)
     */
    fun isValidDate(date: String): Boolean {
        val datePattern = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$"
        return date.matches(datePattern.toRegex())
    }

    /**
     * Validate Indonesian ID Card Number (NIK)
     */
    fun isValidNIK(nik: String): Boolean {
        return nik.length == 16 && nik.all { it.isDigit() }
    }

    /**
     * Validate credit card number using Luhn algorithm
     */
    fun isValidCreditCard(cardNumber: String): Boolean {
        val cleaned = cardNumber.replace("\\s".toRegex(), "")
        if (!cleaned.matches("^[0-9]{13,19}$".toRegex())) return false

        var sum = 0
        var alternate = false

        for (i in cleaned.length - 1 downTo 0) {
            var digit = cleaned[i].toString().toInt()

            if (alternate) {
                digit *= 2
                if (digit > 9) digit -= 9
            }

            sum += digit
            alternate = !alternate
        }

        return sum % 10 == 0
    }

    /**
     * Check if string contains SQL injection patterns
     */
    fun containsSQLInjection(input: String): Boolean {
        val sqlPatterns = listOf(
            "('.+--)|(--)|(;)|(/\\*(?:.|[\\n\\r])*?\\*/)",
            "\\b(ALTER|CREATE|DELETE|DROP|EXEC(UTE)?|INSERT( +INTO)?|MERGE|SELECT|UPDATE|UNION( +ALL)?)\\b"
        )

        return sqlPatterns.any { pattern ->
            Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(input).find()
        }
    }

    /**
     * Check if string contains XSS patterns
     */
    fun containsXSS(input: String): Boolean {
        val xssPatterns = listOf(
            "<script[^>]*>.*?</script>",
            "javascript:",
            "onerror=",
            "onload=",
            "onclick=",
            "<iframe",
            "<object",
            "<embed"
        )

        return xssPatterns.any { pattern ->
            Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(input).find()
        }
    }

    /**
     * Comprehensive input validation
     */
    data class ValidationResult(
        val isValid: Boolean,
        val errorMessage: String? = null
    )

    /**
     * Validate input with multiple rules
     */
    fun validateInput(
        input: String,
        fieldName: String,
        minLength: Int? = null,
        maxLength: Int? = null,
        isEmail: Boolean = false,
        isPhone: Boolean = false,
        isNumeric: Boolean = false,
        isAlphabetic: Boolean = false,
        checkSQLInjection: Boolean = true,
        checkXSS: Boolean = true
    ): ValidationResult {

        if (input.isEmpty()) {
            return ValidationResult(false, "$fieldName cannot be empty")
        }

        minLength?.let {
            if (input.length < it) {
                return ValidationResult(false, "$fieldName must be at least $it characters")
            }
        }

        maxLength?.let {
            if (input.length > it) {
                return ValidationResult(false, "$fieldName cannot exceed $it characters")
            }
        }

        if (isEmail && !isValidEmail(input)) {
            return ValidationResult(false, "Invalid email format")
        }

        if (isPhone && !isValidPhoneNumber(input)) {
            return ValidationResult(false, "Invalid phone number format")
        }

        if (isNumeric && !isValidNumeric(input)) {
            return ValidationResult(false, "$fieldName must contain only numbers")
        }

        if (isAlphabetic && !isValidAlphabetic(input)) {
            return ValidationResult(false, "$fieldName must contain only letters")
        }

        if (checkSQLInjection && containsSQLInjection(input)) {
            return ValidationResult(false, "Invalid characters detected")
        }

        if (checkXSS && containsXSS(input)) {
            return ValidationResult(false, "Invalid characters detected")
        }

        return ValidationResult(true)
    }
}

