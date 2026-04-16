# EnchantmentLevelBreakPatched - 修改说明

## 一、原版信息

**原版仓库**: https://github.com/xiaoliziawa/EnchantmentLevelBreak-architectury/tree/1.21.11

**原版功能**:

| 功能 | 说明 |
|------|------|
| 附魔等级突破255 | 支持超过原版255等级限制的附魔 |
| 铁砧附魔书堆叠 | 可将多个附魔书在铁砧中合并升级 |
| 原版风格堆叠 | 同级附魔书合并时等级+1 (如IV+IV=V) |
| 完全堆叠 | 可将任意等级附魔书叠加 (如IV+V=VIII) |
| 罗马数字显示 | 可选择用罗马数字显示附魔等级 |
| 附魔台附魔 | 附魔台可对已附魔的物品进行附魔 |

---

## 二、改版后功能差异

**功能变化**:

| 功能 | 原版 | 改版 |
|------|------|------|
| 附魔台三级选项 | 三级选项完全相同，无法选择 | 三级选项正常显示不同等级 |
| 附魔台重复附魔 | 强制允许，会覆盖原有附魔 | 改为配置开关，可选择开启或关闭 |

**新增配置项**:

| 配置项 | 默认值 | 说明 |
|--------|--------|------|
| allowEnchantmentTableRestacking | false | 是否允许附魔台对已附魔的物品进行附魔 |

- false = 禁止重复附魔（与原版一致）
- true = 允许重复附魔，按堆叠规则叠加

**堆叠规则说明**:

| 配置 | 效果 |
|------|------|
| allowLevelStacking = true | 完全堆叠 (IV+IV=VIII) |
| allowVanillaLevelStacking = true | 原版堆叠 (IV+IV=V) |

---

## 三、版本信息

| 项目 | 原版 | 改版 |
|------|------|------|
| 版本号 | 1.6 | 1.6.1-modified |
| 名称 | Enchantment Level Break | Enchantment Level Break Patched |
