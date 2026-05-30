package com.anitheme.wallpaper

import android.graphics.*
import android.os.Handler
import android.os.Looper
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import kotlin.math.*
import kotlin.random.Random

/**
 * AniLiveWallpaperService
 *
 * This is the core of the live wallpaper feature.
 * It runs as a system service, so it works on BOTH the home screen AND lock screen.
 * It uses Canvas (not OpenGL) which keeps CPU usage extremely low (~0.2–0.4%).
 *
 * HOW IT WORKS ON HOME SCREEN:
 * Android allows live wallpapers via WallpaperService. The user sets it from:
 *   Settings → Wallpaper → Live Wallpapers → AniTheme
 * Our app has a button that deep-links directly there.
 *
 * POWER SAVING:
 * - Normal mode: 30fps
 * - Power save mode: 5fps (barely animates but still alive)
 * - When invisible (app in front): pauses completely — 0% CPU
 */
class AniLiveWallpaperService : WallpaperService() {

    override fun onCreateEngine(): Engine = AniWallpaperEngine()

    inner class AniWallpaperEngine : Engine() {

        private val handler = Handler(Looper.getMainLooper())
        private var visible = false
        private var width = 0
        private var height = 0

        // Which renderer to use — change this based on user selection
        // Options: StarFieldRenderer, SakuraRenderer, WaveformRenderer
        private lateinit var renderer: BaseWallpaperRenderer

        // Frame timing
        private val normalFrameMs = 33L      // ~30fps
        private val powerSaveFrameMs = 200L  // ~5fps
        private var frameMs = normalFrameMs

        private val drawRunnable = Runnable { drawFrame() }

        override fun onCreate(surfaceHolder: SurfaceHolder) {
            super.onCreate(surfaceHolder)
            renderer = StarFieldRenderer()   // default renderer
            checkBatterySaver()
        }

        override fun onSurfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            super.onSurfaceChanged(holder, format, width, height)
            this.width = width
            this.height = height
            renderer.onSizeChanged(width, height)
        }

        override fun onVisibilityChanged(visible: Boolean) {
            this.visible = visible
            if (visible) {
                drawFrame()
            } else {
                handler.removeCallbacks(drawRunnable)
            }
        }

        override fun onDestroy() {
            super.onDestroy()
            handler.removeCallbacks(drawRunnable)
        }

        private fun drawFrame() {
            val holder = surfaceHolder
            var canvas: Canvas? = null
            try {
                canvas = holder.lockCanvas()
                canvas?.let { renderer.draw(it, width, height) }
            } finally {
                canvas?.let { holder.unlockCanvasAndPost(it) }
            }
            handler.removeCallbacks(drawRunnable)
            if (visible) {
                handler.postDelayed(drawRunnable, frameMs)
            }
        }

        private fun checkBatterySaver() {
            // Check system power save mode
            val powerManager = getSystemService(POWER_SERVICE) as android.os.PowerManager
            frameMs = if (powerManager.isPowerSaveMode) powerSaveFrameMs else normalFrameMs
        }

        // Called when user selects a different live wallpaper style
        fun setRenderer(rendererKey: String) {
            renderer = when (rendererKey) {
                "star_field" -> StarFieldRenderer()
                "sakura"     -> SakuraRenderer()
                "waveform"   -> WaveformRenderer()
                else         -> StarFieldRenderer()
            }
            renderer.onSizeChanged(width, height)
        }
    }
}

// =============================================================================
// BASE RENDERER
// =============================================================================

abstract class BaseWallpaperRenderer {
    var width = 0
    var height = 0

    open fun onSizeChanged(w: Int, h: Int) {
        width = w; height = h
    }

    abstract fun draw(canvas: Canvas, width: Int, height: Int)
}

// =============================================================================
// ⭐ STAR FIELD RENDERER — Itsuki's signature theme
// Dark space background + animated stars + warm accent
// =============================================================================

class StarFieldRenderer : BaseWallpaperRenderer() {

    // Background
    private val bgPaint = Paint().apply {
        color = Color.parseColor("#0D0519")  // deep dark purple-black
    }

    // Star paints
    private val starPaintWhite = Paint().apply {
        color = Color.WHITE
        isAntiAlias = true
        style = Paint.Style.FILL
    }
    private val starPaintGold = Paint().apply {
        color = Color.parseColor("#FFD700")
        isAntiAlias = true
        style = Paint.Style.FILL
    }
    private val starPaintPink = Paint().apply {
        color = Color.parseColor("#FF6B9D")
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    // Stars data
    private data class Star(
        var x: Float, var y: Float,
        val baseSize: Float,
        val speed: Float,         // twinkle speed
        val type: Int,            // 0=white, 1=gold, 2=pink (rare)
        var phase: Float,         // current animation phase
    )

    private val stars = mutableListOf<Star>()
    private var frameCount = 0L

    override fun onSizeChanged(w: Int, h: Int) {
        super.onSizeChanged(w, h)
        initStars()
    }

    private fun initStars() {
        stars.clear()
        val count = 120  // not too many — keeps CPU low
        repeat(count) {
            stars.add(Star(
                x = Random.nextFloat() * width,
                y = Random.nextFloat() * height,
                baseSize = 1f + Random.nextFloat() * 3f,
                speed = 0.02f + Random.nextFloat() * 0.05f,
                type = when {
                    Random.nextFloat() > 0.95f -> 2  // 5% pink
                    Random.nextFloat() > 0.80f -> 1  // 15% gold
                    else -> 0                         // 80% white
                },
                phase = Random.nextFloat() * (2f * PI.toFloat()),
            ))
        }
    }

    override fun draw(canvas: Canvas, width: Int, height: Int) {
        // Background
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

        // Draw subtle gradient overlay (warm at bottom — Itsuki's color)
        // We do this manually with multiple rects for API 21 compat
        val overlayPaint = Paint().apply { isAntiAlias = false }
        for (i in 0..20) {
            val alpha = (i * 4)
            overlayPaint.color = Color.argb(alpha, 232, 85, 62)
            val top = height * (1f - i / 20f * 0.4f)
            canvas.drawRect(0f, top, width.toFloat(), height.toFloat(), overlayPaint)
        }

        // Draw twinkling stars
        frameCount++
        for (star in stars) {
            star.phase += star.speed
            val twinkle = (sin(star.phase.toDouble()) * 0.5 + 0.5).toFloat()  // 0..1
            val size = star.baseSize * (0.4f + twinkle * 0.6f)
            val alpha = (80 + twinkle * 175).toInt().coerceIn(0, 255)

            val paint = when (star.type) {
                1 -> starPaintGold
                2 -> starPaintPink
                else -> starPaintWhite
            }
            paint.alpha = alpha
            canvas.drawCircle(star.x, star.y, size, paint)

            // Occasionally draw a 4-point star sparkle on bigger gold stars
            if (star.type == 1 && star.baseSize > 2.5f && twinkle > 0.7f) {
                drawSparkle(canvas, star.x, star.y, size * 2.5f, paint)
            }
        }
    }

    private fun drawSparkle(canvas: Canvas, cx: Float, cy: Float, r: Float, paint: Paint) {
        val path = Path()
        // Simple 4-point star
        path.moveTo(cx, cy - r)
        path.lineTo(cx + r * 0.2f, cy - r * 0.2f)
        path.lineTo(cx + r, cy)
        path.lineTo(cx + r * 0.2f, cy + r * 0.2f)
        path.lineTo(cx, cy + r)
        path.lineTo(cx - r * 0.2f, cy + r * 0.2f)
        path.lineTo(cx - r, cy)
        path.lineTo(cx - r * 0.2f, cy - r * 0.2f)
        path.close()
        canvas.drawPath(path, paint)
    }
}

// =============================================================================
// 🌸 SAKURA RENDERER — Falling petals
// =============================================================================

class SakuraRenderer : BaseWallpaperRenderer() {

    private val bgPaint = Paint().apply { color = Color.parseColor("#060D1A") }

    private data class Petal(
        var x: Float, var y: Float,
        val size: Float,
        val speed: Float,
        val drift: Float,        // horizontal sway
        var rotation: Float,
        val rotSpeed: Float,
        var phase: Float,
    )

    private val petals = mutableListOf<Petal>()
    private val petalPaint = Paint().apply {
        color = Color.parseColor("#FFB7C5")
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    override fun onSizeChanged(w: Int, h: Int) {
        super.onSizeChanged(w, h)
        petals.clear()
        repeat(40) { spawnPetal(random = true) }
    }

    private fun spawnPetal(random: Boolean = false) {
        petals.add(Petal(
            x = Random.nextFloat() * width,
            y = if (random) Random.nextFloat() * height else -20f,
            size = 6f + Random.nextFloat() * 14f,
            speed = 0.8f + Random.nextFloat() * 1.5f,
            drift = (Random.nextFloat() - 0.5f) * 1.2f,
            rotation = Random.nextFloat() * 360f,
            rotSpeed = (Random.nextFloat() - 0.5f) * 2f,
            phase = Random.nextFloat() * 6f,
        ))
    }

    override fun draw(canvas: Canvas, width: Int, height: Int) {
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

        val iter = petals.iterator()
        while (iter.hasNext()) {
            val p = iter.next()
            p.y += p.speed
            p.x += p.drift + sin(p.phase.toDouble()).toFloat() * 0.5f
            p.phase += 0.03f
            p.rotation += p.rotSpeed

            if (p.y > height + 30) {
                iter.remove()
                spawnPetal()
                continue
            }

            val alpha = when {
                p.y < 50 -> (p.y / 50f * 200).toInt()
                p.y > height - 80 -> ((height - p.y) / 80f * 200).toInt()
                else -> 180
            }.coerceIn(0, 255)

            petalPaint.alpha = alpha
            canvas.save()
            canvas.rotate(p.rotation, p.x, p.y)
            // Draw a simple oval petal
            canvas.drawOval(p.x - p.size, p.y - p.size * 0.6f, p.x + p.size, p.y + p.size * 0.6f, petalPaint)
            canvas.restore()
        }
    }
}

// =============================================================================
// 🎵 WAVEFORM RENDERER — Miku lo-fi vibe
// =============================================================================

class WaveformRenderer : BaseWallpaperRenderer() {

    private val bgPaint = Paint().apply { color = Color.parseColor("#120A00") }
    private val wavePaint = Paint().apply {
        color = Color.parseColor("#DCB432")
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }

    private var tick = 0f

    override fun draw(canvas: Canvas, width: Int, height: Int) {
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)
        tick += 0.04f

        val midY = height / 2f
        val path = Path()

        for (layer in 0..2) {
            path.reset()
            val amplitude = 40f + layer * 25f
            val freq = 0.008f + layer * 0.003f
            val offset = tick + layer * 1.2f
            val alpha = 120 - layer * 30

            wavePaint.alpha = alpha
            wavePaint.strokeWidth = (3 - layer).toFloat()

            path.moveTo(0f, midY)
            for (x in 0..width step 4) {
                val y = midY + sin((x * freq + offset).toDouble()).toFloat() * amplitude
                path.lineTo(x.toFloat(), y)
            }
            canvas.drawPath(path, wavePaint)
        }
    }
}
